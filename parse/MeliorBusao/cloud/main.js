function getDriverEvaluation(driver) {
    return driver;
}

function getBusLoadEvaluation(loaded) {
    return loaded? 0 : 1;
}

Parse.Cloud.define("getAllSummaries", function(request, status) {
	var ratingTable = "Rating";
	var results     = [];
	var chunk_size  = 1000;
	
	var processCallback = function(res) {
		results = results.concat(res);
		if (res.length === chunk_size) { 
			process(res[res.length-1].updatedAt);
		} else {
			var routes = {};
			
			for (var i = 0; i < results.length; ++i) {
				var rota = results[i].get("rota");
				if (!routes.hasOwnProperty(rota)) {
					routes[rota] = {
							count: 0,
							media: 0,
							totalMotorista: 0,
							totalLotacao: 0,
							totalCondition: 0
					};
				}

				var sumario = routes[rota];
				sumario.count++;

				if (results[i].get("motorista"))
					sumario.totalMotorista += 1;
				if (!results[i].get("lotacao"))
					sumario.totalLotacao += 1;
				if (results[i].get("condition"))
					sumario.totalCondition += 1;
			}
			status.success(routes); 
		}
	};

	var process = function(skip) {
		var query = new Parse.Query(ratingTable);
		if (skip) {
			query.greaterThan("objectId", skip);
		}
		query.limit(chunk_size);
		query.ascending("objectId");
		query.find().then(function (res) {
			processCallback(res);
		}, function (error) {
			status.error("query unsuccessful, length of result " + results.length + ", error:" + error.code + " " + error.message);
		});
	};
	process(false);
});


Parse.Cloud.define("sumarios", function(request, response) {
    var query = new Parse.Query("Rating");
    query.find({
        success: function(results) {
            var routes = {};

            for (var i = 0; i < results.length; ++i) {
                var rota = results[i].get("rota");

                if (!routes.hasOwnProperty(rota)) {
                    routes[rota] = {
                        count: 0,
                        media: 0,
                        totalMotorista: 0,
                        totalLotacao: 0
                    };
                }

                var sumario = routes[rota];

                sumario.count++;
                sumario.media += results[i].get("nota");

                if (results[i].get("motorista"))
                    sumario.totalMotorista += 1;
                if (results[i].get("lotacao"))
                    sumario.totalLotacao += 1;
            }

            response.success(routes);
        },
        error: function() {
            response.error("rota lookup failed");
        }
    });
});

Parse.Cloud.define("verifyBigSeaToken", function(request, response) {
	var bigSeatoken = request.params.token;
	var username = request.params.username;

	Parse.Cloud.httpRequest({
	  method: 'POST',
	  url: 'xxxxxxxxxxxxxxxxxxxxxxxxx',
	  params: {
		token : bigSeatoken
	  }
	}).then(function(httpResponse) {
		if (httpResponse.data.response == username) {
			console.log(httpResponse.data.response);
			response.success(true);
		} else {
			console.error('Token does not belong to user');
			response.error('invalid token');
		}
	}, function(httpResponse) {
		console.error('Request failed with response code ' + httpResponse.status);
		response.error(httpResponse.status);
	});
});

Parse.Cloud.define("verifyGoogleToken", function(request, response) {
	var token = request.params.token;
	
	Parse.Cloud.httpRequest({
	  method: 'POST',
	  url: 'https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=' + token,
	}).then(function(httpResponse) {
		console.log(httpResponse.text);
		response.success(true);	
	}, function(httpResponse) {
		console.error('Request failed with response code ' + httpResponse.status);
		response.error('invalid token');
	});

});

Parse.Cloud.define("insertRating", function(request, response) {
        var token = request.params.token;
        var username = request.params.username;
        var authenticationProvider = request.params.authenticationProvider;
        var ratings = JSON.parse(request.params.ratings);

        var Rating = Parse.Object.extend("Rating");

//      Create new Rating objects for each rating passed in the parameter
        var ratingsToSave = [];
        for (var i = 0; i < ratings.length; i++) {
                var rating = new Rating();
                var atributes = ratings[i];
                rating.set(atributes);
                ratingsToSave.push(rating);
        }

        var functionToCall = "verify" + authenticationProvider + "Token";

        Parse.Cloud.run(functionToCall, {token: token, username: username})
        .then(function(tokenValidationResponse) {
                        Parse.Object.saveAll(ratingsToSave, {
                                success: function(savingResponse) {
                                        console.log(ratings.length + " objects have been saved successfully");
                                        response.success("done");
                                },
                                error: function(error) {
                                        if (error.message == "the rating already exists") {
                                                 response.success("done");
                                        } else {
                                                console.error("The save failed.");
                                                response.error(error);
                                        }
                        }
                });
                }, function(tokenValidationResponse) {
                        console.error("The save failed: " + tokenValidationResponse.message);
                        response.error("The save failed: " + tokenValidationResponse.message);
                }
        );
});

Parse.Cloud.beforeSave("Rating", function(request, response) {
        var tripId = request.object.get("tripId");
        var route = request.object.get("rota");

        var Rating = Parse.Object.extend("Rating");
        var query = new Parse.Query(Rating);
        query.equalTo("tripId", tripId);
        query.equalTo("rota", route);

        query.first({
                success: function(object) {
                        if (object == null) {
                                response.success();
                        } else {
                                response.error("the rating already exists");
                        }
                },
                error: function(error) {
                        response.error(error.message);
                }
        });
});







