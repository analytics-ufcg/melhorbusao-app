package br.edu.ufcg.analytics.meliorbusao.models.otp;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ufcg.analytics.meliorbusao.fragments.MapFragment;

public class Itinerary {
    private List<String> busRoutes;
    private String departureBusStop;
    private Date departureTime;
    private Date arrivalTime;
    private int durationInSecs;
    private MapFragment map;

    public Itinerary(List<String> busRoutes, String departureBusStop, Date departureTime, Date arrivalTime, int durationInSecs) {
        this.busRoutes = busRoutes;
        this.departureBusStop = departureBusStop;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationInSecs = durationInSecs;
        map = new MapFragment();
    }

    public List<String> getBusRoutes() {
        return busRoutes;
    }

    public void setBusRoutes(List<String> busRoutes) {
        this.busRoutes = busRoutes;
    }

    public String getDepartureBusStop() {
        return departureBusStop;
    }

    public void setDepartureBusStop(String departureBusStop) {
        this.departureBusStop = departureBusStop;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDurationInSecs() {
        return durationInSecs;
    }

    public void setDurationInSecs(int durationInSecs) {
        this.durationInSecs = durationInSecs;
    }

    public static Itinerary fromJson(JSONObject itineraryJson) {
        Itinerary itinerary = null;

        try {
            List<String> busRoutes = new ArrayList<String>();
            JSONArray legsJson = itineraryJson.getJSONArray("legs");
            JSONObject firstBusLeg = null;

            for (int i = 0; i < legsJson.length(); i++) {
                JSONObject legJson = legsJson.getJSONObject(i);
                String mode = legJson.getString("mode");
                if (mode.equals("BUS")) {
                    if (firstBusLeg == null) firstBusLeg = legJson;
                    String route = legJson.getString("route");
                    busRoutes.add(route);
                }
            }
            String depBusStop = firstBusLeg.getJSONObject("from").getString("name");
            Date startTime = new Date(itineraryJson.getLong("startTime"));
            Date endTime = new Date(itineraryJson.getLong("endTime"));
            int duration = itineraryJson.getInt("duration");

            itinerary = new Itinerary(busRoutes, depBusStop, startTime, endTime, duration);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itinerary;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Routes: ");
        for (int i = 0; i < busRoutes.size(); i++) {
            if (i != 0) sb.append(",");
            sb.append(busRoutes.get(i));
        }
        sb.append("\n");
        sb.append("Departure Bus Stop: ");
        sb.append(departureBusStop);
        sb.append("\n");
        sb.append("Start Time: ");
        sb.append(departureTime);
        sb.append("\n");
        sb.append("End Time: ");
        sb.append(arrivalTime);
        sb.append("\n");
        sb.append("Duration (in secs): ");
        sb.append(durationInSecs);
        return sb.toString();
    }

//    /**
//     * Returns a list that contains solely the {@link #map}.
//     */
//    @Override
//    public List<Object> getChildObjectList() {
//        List<Object> list = new ArrayList<>();
//        list.add(map);
//        return list;
//    }
//
    /**
     * Set the first element from a list to be the {@link #map}.
     *
     * @param list A list that shoul contains only one {@link MapFragment}. If the list passed
     *             contains more than one {@link MapFragment}, the first one will be set as the
     *             {@link #map}.
     */
//    @Override
//    public void setChildObjectList(List<Object> list) {
//        if (list != null && !list.isEmpty()) {
//            if (list.get(0) instanceof MapFragment) {
//                map = (MapFragment) list.get(0);
//            }
//        }
//    }

    public static void main() {
        JSONObject itJson = null;
        try {
            itJson = new JSONObject("{\"requestParameters\":{\"mode\":\"TRANSIT,WALK\",\"date\":\"04/03/2017\",\"fromPlace\":\"-25.39211,-49.22613\",\"toPlace\":\"-25.45102,-49.28381\",\"time\":\"16:20:00\"},\"plan\":{\"date\":1491247200000,\"from\":{\"name\":\"Origin\",\"lon\":-49.22613,\"lat\":-25.39211,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"to\":{\"name\":\"Destination\",\"lon\":-49.28381,\"lat\":-25.45102,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"itineraries\":[{\"duration\":2828,\"startTime\":1491247281000,\"endTime\":1491250109000,\"walkTime\":386,\"transitTime\":2153,\"waitingTime\":289,\"walkDistance\":501.4348345853744,\"walkLimitExceeded\":false,\"elevationLost\":0.0,\"elevationGained\":0.0,\"transfers\":1,\"legs\":[{\"startTime\":1491247281000,\"endTime\":1491247318000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":48.309,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Origin\",\"lon\":-49.22613,\"lat\":-25.39211,\"departure\":1491247281000,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"to\":{\"name\":\"Av. Prefeito Erasto Gaertner, 2419 - Bacacheri\",\"stopId\":\"1:29017\",\"stopCode\":\"130022\",\"lon\":-49.226471341105,\"lat\":-25.392424865256,\"arrival\":1491247318000,\"departure\":1491247319000,\"stopIndex\":18,\"stopSequence\":19,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"nl~yCvmmkHp@lA\",\"length\":2},\"rentedBike\":false,\"transitLeg\":false,\"duration\":37.0,\"steps\":[{\"distance\":48.309,\"relativeDirection\":\"DEPART\",\"streetName\":\"Avenida Prefeito Erasto Gaertner\",\"absoluteDirection\":\"SOUTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2260325991661,\"lat\":-25.392231424976803,\"elevation\":[]}]},{\"startTime\":1491247319000,\"endTime\":1491247800000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":1930.4420495642019,\"pathway\":false,\"mode\":\"BUS\",\"route\":\"222\",\"agencyName\":\"URBS\",\"agencyUrl\":\"http://www.urbs.curitiba.pr.gov.br\",\"agencyTimeZoneOffset\":-10800000,\"routeColor\":\"F98700\",\"routeType\":3,\"routeId\":\"1:328\",\"routeTextColor\":\"000000\",\"interlineWithPreviousLeg\":false,\"headsign\":\"Terminal Boa Vista\",\"agencyId\":\"1\",\"tripId\":\"1:3614446\",\"serviceDate\":\"20170403\",\"from\":{\"name\":\"Av. Prefeito Erasto Gaertner, 2419 - Bacacheri\",\"stopId\":\"1:29017\",\"stopCode\":\"130022\",\"lon\":-49.226471341105,\"lat\":-25.392424865256,\"arrival\":1491247318000,\"departure\":1491247319000,\"stopIndex\":18,\"stopSequence\":19,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Terminal Boa Vista - 222 - Vila Esperança\",\"stopId\":\"1:30450\",\"stopCode\":\"104205\",\"lon\":-49.241172069941,\"lat\":-25.393203307563,\"arrival\":1491247800000,\"departure\":1491247800000,\"stopIndex\":26,\"stopSequence\":27,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"~m~yCfpmkH\\\\l@Zj@Vb@Xd@n@hA\\\\l@p@jA`AfBVd@x@vArA|B|@|ALTP\\\\^n@z@zA~@`BjArBPXZj@JNr@nAr@pAdBvCVf@@BZh@t@rA_Ax@eAt@e@Vj@~@\\\\n@d@z@FHVf@a@\\\\o@f@q@d@g@^e@^yCvBaBlAq@f@a@XWRSNs@f@WPOL]VSTgA|Ag@r@MPKPIHo@q@A?CAC@CBKHOTW`@\",\"length\":66},\"routeShortName\":\"222\",\"routeLongName\":\"V. ESPERANÇA\",\"rentedBike\":false,\"transitLeg\":true,\"duration\":481.0,\"steps\":[]},{\"startTime\":1491247800000,\"endTime\":1491247863000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":79.84700000000001,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Terminal Boa Vista - 222 - Vila Esperança\",\"stopId\":\"1:30450\",\"stopCode\":\"104205\",\"lon\":-49.241172069941,\"lat\":-25.393203307563,\"arrival\":1491247800000,\"departure\":1491247800000,\"stopIndex\":26,\"stopSequence\":27,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Terminal Boa Vista - 204 - Sta. Cândida / Pinheirinho\",\"stopId\":\"1:30446\",\"stopCode\":\"109081\",\"lon\":-49.241217550926,\"lat\":-25.393294250054,\"arrival\":1491247863000,\"departure\":1491248150000,\"stopIndex\":1,\"stopSequence\":2,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"dq~yC|mpkHt@v@FKd@o@\",\"length\":4},\"rentedBike\":false,\"transitLeg\":false,\"duration\":63.0,\"steps\":[{\"distance\":40.151,\"relativeDirection\":\"DEPART\",\"streetName\":\"Rua Jovino do Rosário\",\"absoluteDirection\":\"SOUTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.24142885083274,\"lat\":-25.39298809286274,\"elevation\":[]},{\"distance\":39.696,\"relativeDirection\":\"LEFT\",\"streetName\":\"Rua João Havro\",\"absoluteDirection\":\"SOUTHEAST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2417007,\"lat\":-25.393252800000003,\"elevation\":[]}]},{\"startTime\":1491248150000,\"endTime\":1491249822000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":8535.0109571436,\"pathway\":false,\"mode\":\"BUS\",\"route\":\"204\",\"agencyName\":\"URBS\",\"agencyUrl\":\"http://www.urbs.curitiba.pr.gov.br\",\"agencyTimeZoneOffset\":-10800000,\"routeColor\":\"FF0000\",\"routeType\":3,\"routeId\":\"1:213\",\"routeTextColor\":\"000000\",\"interlineWithPreviousLeg\":false,\"headsign\":\"Terminal Pinheirinho\",\"agencyId\":\"1\",\"tripId\":\"1:3620958\",\"serviceDate\":\"20170403\",\"from\":{\"name\":\"Terminal Boa Vista - 204 - Sta. Cândida / Pinheirinho\",\"stopId\":\"1:30446\",\"stopCode\":\"109081\",\"lon\":-49.241217550926,\"lat\":-25.393294250054,\"arrival\":1491247863000,\"departure\":1491248150000,\"stopIndex\":1,\"stopSequence\":2,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Estação Tubo Água Verde / Iguaçu\",\"stopId\":\"1:25740\",\"stopCode\":\"109003\",\"lon\":-49.285369401301,\"lat\":-25.448301917892,\"arrival\":1491249822000,\"departure\":1491249823000,\"stopIndex\":6,\"stopSequence\":7,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"tr~yCflpkHGJGHADAD@L@FBD@DFFTRMNLLn@l@fAbAv@r@`@\\\\`B|AxApA\\\\ZXX`@^^\\\\f@b@d@b@`Ax@~@z@z@t@x@r@pBbBv@r@r@l@x@t@v@r@t@r@VV`@`@n@j@v@r@PR^\\\\z@v@tBlBz@p@bAx@bCzBbA~@~@x@nBdBt@r@\\\\\\\\TVV^R\\\\LZNn@H`AfAy@n@m@n@k@DHFFNNNLpAjA^`@\\\\\\\\TRLJLHmAdBl@h@j@h@f@d@p@j@y@jAa@l@e@n@b@^h@f@PNPL`@PTH^Nh@HRBn@Jt@Jn@HhAPz@Lt@Nb@Hb@L^TVR\\\\`@^b@l@t@x@`A|AtAr@l@XVTRl@j@r@n@n@j@f@d@pAjAlAfAlFxEfAbAbA|@v@r@bA~@fB`B^\\\\`@`@f@`@ZZLLHLDNDLBR?RCRERMj@CT?PBX@ZBVHhAB\\\\A~@K~@MnAK`AIdAIz@E^Gb@CVv@Jj@Hh@F`AN`@F^Fx@L|@Ll@JJ@\\\\FjANhATt@Jd@Q^F`@Fd@HdBXfAPTDFi@Lu@Fg@@IJc@N_@RUf@w@\\\\e@Xc@P]NUT]h@w@f@u@h@y@f@s@HKX[ZQTK|@Y`Be@nA]b@KRGHENIHGJKRSXYJMVUPGb@OPGz@Wx@Ur@Q`@Kd@OrAa@nCcAf@Op@Yj@Wj@Sx@YlAa@r@W|Ag@`@MzBs@xBq@z@Yf@OVK\\\\lAZfAZfAp@xBV`AV|@zD{Ap@YZMNGbAWjAe@dAa@^SZ[ZM\\\\Md@SnAg@nBw@x@pCl@vBd@`BPl@T`Ad@bBd@dBv@tCLd@`@rAL\\\\Lf@XhAXz@XbAHRJ^Vt@z@pCNj@V|@h@zBLXZfA`@tAd@nBTv@Tt@Rt@HVt@bCXfANh@V|@f@hBl@vBX~@Rt@x@vCv@nCV`A\\\\hAL`@N^FJTv@`@zAXdAXdAt@jCd@hBb@fBHVXfATv@V|@^xARv@Lb@HX\\\\nAf@rBNj@\",\"length\":308},\"routeShortName\":\"204\",\"routeLongName\":\"STA. CÂNDIDA / PINHEIRINHO\",\"rentedBike\":false,\"transitLeg\":true,\"duration\":1672.0,\"steps\":[]},{\"startTime\":1491249823000,\"endTime\":1491250109000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":373.245,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Estação Tubo Água Verde / Iguaçu\",\"stopId\":\"1:25740\",\"stopCode\":\"109003\",\"lon\":-49.285369401301,\"lat\":-25.448301917892,\"arrival\":1491249822000,\"departure\":1491249823000,\"stopIndex\":6,\"stopSequence\":7,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Destination\",\"lon\":-49.28381,\"lat\":-25.45102,\"arrival\":1491250109000,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"legGeometry\":{\"points\":\"pjizCv`ykHQq@hIsCtFoB\",\"length\":4},\"rentedBike\":false,\"transitLeg\":false,\"duration\":286.0,\"steps\":[{\"distance\":27.478,\"relativeDirection\":\"DEPART\",\"streetName\":\"Avenida Iguaçu\",\"absoluteDirection\":\"EAST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2853946992316,\"lat\":-25.448246545446064,\"elevation\":[]},{\"distance\":345.767,\"relativeDirection\":\"RIGHT\",\"streetName\":\"Rua Doutor Alexandre Gutierrez\",\"absoluteDirection\":\"SOUTH\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.285141700000004,\"lat\":-25.4481523,\"elevation\":[]}]}],\"tooSloped\":false},{\"duration\":3514,\"startTime\":1491247355000,\"endTime\":1491250869000,\"walkTime\":421,\"transitTime\":2119,\"waitingTime\":974,\"walkDistance\":549.2258345853745,\"walkLimitExceeded\":false,\"elevationLost\":0.0,\"elevationGained\":0.0,\"transfers\":1,\"legs\":[{\"startTime\":1491247355000,\"endTime\":1491247392000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":48.309,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Origin\",\"lon\":-49.22613,\"lat\":-25.39211,\"departure\":1491247355000,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"to\":{\"name\":\"Av. Prefeito Erasto Gaertner, 2419 - Bacacheri\",\"stopId\":\"1:29017\",\"stopCode\":\"130022\",\"lon\":-49.226471341105,\"lat\":-25.392424865256,\"arrival\":1491247392000,\"departure\":1491247393000,\"stopIndex\":22,\"stopSequence\":23,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"nl~yCvmmkHp@lA\",\"length\":2},\"rentedBike\":false,\"transitLeg\":false,\"duration\":37.0,\"steps\":[{\"distance\":48.309,\"relativeDirection\":\"DEPART\",\"streetName\":\"Avenida Prefeito Erasto Gaertner\",\"absoluteDirection\":\"SOUTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2260325991661,\"lat\":-25.392231424976803,\"elevation\":[]}]},{\"startTime\":1491247393000,\"endTime\":1491247860000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":1900.02541971524,\"pathway\":false,\"mode\":\"BUS\",\"route\":\"342\",\"agencyName\":\"URBS\",\"agencyUrl\":\"http://www.urbs.curitiba.pr.gov.br\",\"agencyTimeZoneOffset\":-10800000,\"routeColor\":\"F98700\",\"routeType\":3,\"routeId\":\"1:202\",\"routeTextColor\":\"000000\",\"interlineWithPreviousLeg\":false,\"headsign\":\"Terminal Boa Vista\",\"agencyId\":\"1\",\"tripId\":\"1:3604529\",\"serviceDate\":\"20170403\",\"from\":{\"name\":\"Av. Prefeito Erasto Gaertner, 2419 - Bacacheri\",\"stopId\":\"1:29017\",\"stopCode\":\"130022\",\"lon\":-49.226471341105,\"lat\":-25.392424865256,\"arrival\":1491247392000,\"departure\":1491247393000,\"stopIndex\":22,\"stopSequence\":23,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Terminal Boa Vista - 342 - Bairro Alto/Boa Vista\",\"stopId\":\"1:30448\",\"stopCode\":\"104203\",\"lon\":-49.240916705523,\"lat\":-25.393374230673,\"arrival\":1491247860000,\"departure\":1491247860000,\"stopIndex\":30,\"stopSequence\":31,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"|m~yCfpmkH^n@^n@^n@\\\\l@^n@\\\\j@n@jAb@v@\\\\n@@@Vd@v@rALT\\\\l@h@~@Xd@LTV`@JRFJFLNVP\\\\\\\\l@Zf@PX\\\\n@R\\\\Vd@LRR\\\\Vd@Zh@HNNTZj@Zj@Zh@R\\\\Xf@Xf@R\\\\PZR\\\\NV`@t@l@fAqAfA]VSNg@V^l@Vd@T`@h@~@?@Xf@s@h@a@ZuA`Ag@^{@n@aBlAs@h@u@h@k@b@m@b@OJIFOJi@^SNUPWRSR]f@]f@Y`@Y`@GJMPWAMMECGAEAE?G@CBGBA@\",\"length\":92},\"routeShortName\":\"342\",\"routeLongName\":\"B. ALTO / BOA VISTA\",\"rentedBike\":false,\"transitLeg\":true,\"duration\":467.0,\"steps\":[]},{\"startTime\":1491247860000,\"endTime\":1491247958000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":127.638,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Terminal Boa Vista - 342 - Bairro Alto/Boa Vista\",\"stopId\":\"1:30448\",\"stopCode\":\"104203\",\"lon\":-49.240916705523,\"lat\":-25.393374230673,\"arrival\":1491247860000,\"departure\":1491247860000,\"stopIndex\":30,\"stopSequence\":31,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Terminal Boa Vista - 204 - Sta. Cândida / Pinheirinho\",\"stopId\":\"1:30446\",\"stopCode\":\"109081\",\"lon\":-49.241217550926,\"lat\":-25.393294250054,\"arrival\":1491247958000,\"departure\":1491248930000,\"stopIndex\":1,\"stopSequence\":2,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"tt~yClipkHf@f@LOHKCPCTILy@hA\",\"length\":8},\"rentedBike\":false,\"transitLeg\":false,\"duration\":98.0,\"steps\":[{\"distance\":49.3,\"relativeDirection\":\"DEPART\",\"streetName\":\"path\",\"absoluteDirection\":\"SOUTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":true,\"lon\":-49.240705518648106,\"lat\":-25.393541806928674,\"elevation\":[]},{\"distance\":20.387,\"relativeDirection\":\"HARD_RIGHT\",\"streetName\":\"bike path\",\"absoluteDirection\":\"WEST\",\"stayOn\":true,\"area\":false,\"bogusName\":true,\"lon\":-49.2407661,\"lat\":-25.3938603,\"elevation\":[]},{\"distance\":57.951,\"relativeDirection\":\"SLIGHTLY_RIGHT\",\"streetName\":\"Rua João Havro\",\"absoluteDirection\":\"NORTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.240965800000005,\"lat\":-25.3938276,\"elevation\":[]}]},{\"startTime\":1491248930000,\"endTime\":1491250582000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":8535.0109571436,\"pathway\":false,\"mode\":\"BUS\",\"route\":\"204\",\"agencyName\":\"URBS\",\"agencyUrl\":\"http://www.urbs.curitiba.pr.gov.br\",\"agencyTimeZoneOffset\":-10800000,\"routeColor\":\"FF0000\",\"routeType\":3,\"routeId\":\"1:213\",\"routeTextColor\":\"000000\",\"interlineWithPreviousLeg\":false,\"headsign\":\"Terminal Pinheirinho\",\"agencyId\":\"1\",\"tripId\":\"1:3620977\",\"serviceDate\":\"20170403\",\"from\":{\"name\":\"Terminal Boa Vista - 204 - Sta. Cândida / Pinheirinho\",\"stopId\":\"1:30446\",\"stopCode\":\"109081\",\"lon\":-49.241217550926,\"lat\":-25.393294250054,\"arrival\":1491247958000,\"departure\":1491248930000,\"stopIndex\":1,\"stopSequence\":2,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Estação Tubo Água Verde / Iguaçu\",\"stopId\":\"1:25740\",\"stopCode\":\"109003\",\"lon\":-49.285369401301,\"lat\":-25.448301917892,\"arrival\":1491250582000,\"departure\":1491250583000,\"stopIndex\":6,\"stopSequence\":7,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"tr~yCflpkHGJGHADAD@L@FBD@DFFTRMNLLn@l@fAbAv@r@`@\\\\`B|AxApA\\\\ZXX`@^^\\\\f@b@d@b@`Ax@~@z@z@t@x@r@pBbBv@r@r@l@x@t@v@r@t@r@VV`@`@n@j@v@r@PR^\\\\z@v@tBlBz@p@bAx@bCzBbA~@~@x@nBdBt@r@\\\\\\\\TVV^R\\\\LZNn@H`AfAy@n@m@n@k@DHFFNNNLpAjA^`@\\\\\\\\TRLJLHmAdBl@h@j@h@f@d@p@j@y@jAa@l@e@n@b@^h@f@PNPL`@PTH^Nh@HRBn@Jt@Jn@HhAPz@Lt@Nb@Hb@L^TVR\\\\`@^b@l@t@x@`A|AtAr@l@XVTRl@j@r@n@n@j@f@d@pAjAlAfAlFxEfAbAbA|@v@r@bA~@fB`B^\\\\`@`@f@`@ZZLLHLDNDLBR?RCRERMj@CT?PBX@ZBVHhAB\\\\A~@K~@MnAK`AIdAIz@E^Gb@CVv@Jj@Hh@F`AN`@F^Fx@L|@Ll@JJ@\\\\FjANhATt@Jd@Q^F`@Fd@HdBXfAPTDFi@Lu@Fg@@IJc@N_@RUf@w@\\\\e@Xc@P]NUT]h@w@f@u@h@y@f@s@HKX[ZQTK|@Y`Be@nA]b@KRGHENIHGJKRSXYJMVUPGb@OPGz@Wx@Ur@Q`@Kd@OrAa@nCcAf@Op@Yj@Wj@Sx@YlAa@r@W|Ag@`@MzBs@xBq@z@Yf@OVK\\\\lAZfAZfAp@xBV`AV|@zD{Ap@YZMNGbAWjAe@dAa@^SZ[ZM\\\\Md@SnAg@nBw@x@pCl@vBd@`BPl@T`Ad@bBd@dBv@tCLd@`@rAL\\\\Lf@XhAXz@XbAHRJ^Vt@z@pCNj@V|@h@zBLXZfA`@tAd@nBTv@Tt@Rt@HVt@bCXfANh@V|@f@hBl@vBX~@Rt@x@vCv@nCV`A\\\\hAL`@N^FJTv@`@zAXdAXdAt@jCd@hBb@fBHVXfATv@V|@^xARv@Lb@HX\\\\nAf@rBNj@\",\"length\":308},\"routeShortName\":\"204\",\"routeLongName\":\"STA. CÂNDIDA / PINHEIRINHO\",\"rentedBike\":false,\"transitLeg\":true,\"duration\":1652.0,\"steps\":[]},{\"startTime\":1491250583000,\"endTime\":1491250869000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":373.245,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Estação Tubo Água Verde / Iguaçu\",\"stopId\":\"1:25740\",\"stopCode\":\"109003\",\"lon\":-49.285369401301,\"lat\":-25.448301917892,\"arrival\":1491250582000,\"departure\":1491250583000,\"stopIndex\":6,\"stopSequence\":7,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Destination\",\"lon\":-49.28381,\"lat\":-25.45102,\"arrival\":1491250869000,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"legGeometry\":{\"points\":\"pjizCv`ykHQq@hIsCtFoB\",\"length\":4},\"rentedBike\":false,\"transitLeg\":false,\"duration\":286.0,\"steps\":[{\"distance\":27.478,\"relativeDirection\":\"DEPART\",\"streetName\":\"Avenida Iguaçu\",\"absoluteDirection\":\"EAST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2853946992316,\"lat\":-25.448246545446064,\"elevation\":[]},{\"distance\":345.767,\"relativeDirection\":\"RIGHT\",\"streetName\":\"Rua Doutor Alexandre Gutierrez\",\"absoluteDirection\":\"SOUTH\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.285141700000004,\"lat\":-25.4481523,\"elevation\":[]}]}],\"tooSloped\":false},{\"duration\":3166,\"startTime\":1491248404000,\"endTime\":1491251570000,\"walkTime\":386,\"transitTime\":2131,\"waitingTime\":649,\"walkDistance\":501.4348345853744,\"walkLimitExceeded\":false,\"elevationLost\":0.0,\"elevationGained\":0.0,\"transfers\":1,\"legs\":[{\"startTime\":1491248404000,\"endTime\":1491248441000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":48.309,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Origin\",\"lon\":-49.22613,\"lat\":-25.39211,\"departure\":1491248404000,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"to\":{\"name\":\"Av. Prefeito Erasto Gaertner, 2419 - Bacacheri\",\"stopId\":\"1:29017\",\"stopCode\":\"130022\",\"lon\":-49.226471341105,\"lat\":-25.392424865256,\"arrival\":1491248441000,\"departure\":1491248442000,\"stopIndex\":18,\"stopSequence\":19,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"nl~yCvmmkHp@lA\",\"length\":2},\"rentedBike\":false,\"transitLeg\":false,\"duration\":37.0,\"steps\":[{\"distance\":48.309,\"relativeDirection\":\"DEPART\",\"streetName\":\"Avenida Prefeito Erasto Gaertner\",\"absoluteDirection\":\"SOUTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2260325991661,\"lat\":-25.392231424976803,\"elevation\":[]}]},{\"startTime\":1491248442000,\"endTime\":1491248940000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":1930.4420495642019,\"pathway\":false,\"mode\":\"BUS\",\"route\":\"222\",\"agencyName\":\"URBS\",\"agencyUrl\":\"http://www.urbs.curitiba.pr.gov.br\",\"agencyTimeZoneOffset\":-10800000,\"routeColor\":\"F98700\",\"routeType\":3,\"routeId\":\"1:328\",\"routeTextColor\":\"000000\",\"interlineWithPreviousLeg\":false,\"headsign\":\"Terminal Boa Vista\",\"agencyId\":\"1\",\"tripId\":\"1:3614487\",\"serviceDate\":\"20170403\",\"from\":{\"name\":\"Av. Prefeito Erasto Gaertner, 2419 - Bacacheri\",\"stopId\":\"1:29017\",\"stopCode\":\"130022\",\"lon\":-49.226471341105,\"lat\":-25.392424865256,\"arrival\":1491248441000,\"departure\":1491248442000,\"stopIndex\":18,\"stopSequence\":19,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Terminal Boa Vista - 222 - Vila Esperança\",\"stopId\":\"1:30450\",\"stopCode\":\"104205\",\"lon\":-49.241172069941,\"lat\":-25.393203307563,\"arrival\":1491248940000,\"departure\":1491248940000,\"stopIndex\":26,\"stopSequence\":27,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"~m~yCfpmkH\\\\l@Zj@Vb@Xd@n@hA\\\\l@p@jA`AfBVd@x@vArA|B|@|ALTP\\\\^n@z@zA~@`BjArBPXZj@JNr@nAr@pAdBvCVf@@BZh@t@rA_Ax@eAt@e@Vj@~@\\\\n@d@z@FHVf@a@\\\\o@f@q@d@g@^e@^yCvBaBlAq@f@a@XWRSNs@f@WPOL]VSTgA|Ag@r@MPKPIHo@q@A?CAC@CBKHOTW`@\",\"length\":66},\"routeShortName\":\"222\",\"routeLongName\":\"V. ESPERANÇA\",\"rentedBike\":false,\"transitLeg\":true,\"duration\":498.0,\"steps\":[]},{\"startTime\":1491248940000,\"endTime\":1491249003000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":79.84700000000001,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Terminal Boa Vista - 222 - Vila Esperança\",\"stopId\":\"1:30450\",\"stopCode\":\"104205\",\"lon\":-49.241172069941,\"lat\":-25.393203307563,\"arrival\":1491248940000,\"departure\":1491248940000,\"stopIndex\":26,\"stopSequence\":27,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Terminal Boa Vista - 204 - Sta. Cândida / Pinheirinho\",\"stopId\":\"1:30446\",\"stopCode\":\"109081\",\"lon\":-49.241217550926,\"lat\":-25.393294250054,\"arrival\":1491249003000,\"departure\":1491249650000,\"stopIndex\":1,\"stopSequence\":2,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"dq~yC|mpkHt@v@FKd@o@\",\"length\":4},\"rentedBike\":false,\"transitLeg\":false,\"duration\":63.0,\"steps\":[{\"distance\":40.151,\"relativeDirection\":\"DEPART\",\"streetName\":\"Rua Jovino do Rosário\",\"absoluteDirection\":\"SOUTHWEST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.24142885083274,\"lat\":-25.39298809286274,\"elevation\":[]},{\"distance\":39.696,\"relativeDirection\":\"LEFT\",\"streetName\":\"Rua João Havro\",\"absoluteDirection\":\"SOUTHEAST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2417007,\"lat\":-25.393252800000003,\"elevation\":[]}]},{\"startTime\":1491249650000,\"endTime\":1491251283000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":8535.0109571436,\"pathway\":false,\"mode\":\"BUS\",\"route\":\"204\",\"agencyName\":\"URBS\",\"agencyUrl\":\"http://www.urbs.curitiba.pr.gov.br\",\"agencyTimeZoneOffset\":-10800000,\"routeColor\":\"FF0000\",\"routeType\":3,\"routeId\":\"1:213\",\"routeTextColor\":\"000000\",\"interlineWithPreviousLeg\":false,\"headsign\":\"Terminal Pinheirinho\",\"agencyId\":\"1\",\"tripId\":\"1:3620997\",\"serviceDate\":\"20170403\",\"from\":{\"name\":\"Terminal Boa Vista - 204 - Sta. Cândida / Pinheirinho\",\"stopId\":\"1:30446\",\"stopCode\":\"109081\",\"lon\":-49.241217550926,\"lat\":-25.393294250054,\"arrival\":1491249003000,\"departure\":1491249650000,\"stopIndex\":1,\"stopSequence\":2,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Estação Tubo Água Verde / Iguaçu\",\"stopId\":\"1:25740\",\"stopCode\":\"109003\",\"lon\":-49.285369401301,\"lat\":-25.448301917892,\"arrival\":1491251283000,\"departure\":1491251284000,\"stopIndex\":6,\"stopSequence\":7,\"vertexType\":\"TRANSIT\"},\"legGeometry\":{\"points\":\"tr~yCflpkHGJGHADAD@L@FBD@DFFTRMNLLn@l@fAbAv@r@`@\\\\`B|AxApA\\\\ZXX`@^^\\\\f@b@d@b@`Ax@~@z@z@t@x@r@pBbBv@r@r@l@x@t@v@r@t@r@VV`@`@n@j@v@r@PR^\\\\z@v@tBlBz@p@bAx@bCzBbA~@~@x@nBdBt@r@\\\\\\\\TVV^R\\\\LZNn@H`AfAy@n@m@n@k@DHFFNNNLpAjA^`@\\\\\\\\TRLJLHmAdBl@h@j@h@f@d@p@j@y@jAa@l@e@n@b@^h@f@PNPL`@PTH^Nh@HRBn@Jt@Jn@HhAPz@Lt@Nb@Hb@L^TVR\\\\`@^b@l@t@x@`A|AtAr@l@XVTRl@j@r@n@n@j@f@d@pAjAlAfAlFxEfAbAbA|@v@r@bA~@fB`B^\\\\`@`@f@`@ZZLLHLDNDLBR?RCRERMj@CT?PBX@ZBVHhAB\\\\A~@K~@MnAK`AIdAIz@E^Gb@CVv@Jj@Hh@F`AN`@F^Fx@L|@Ll@JJ@\\\\FjANhATt@Jd@Q^F`@Fd@HdBXfAPTDFi@Lu@Fg@@IJc@N_@RUf@w@\\\\e@Xc@P]NUT]h@w@f@u@h@y@f@s@HKX[ZQTK|@Y`Be@nA]b@KRGHENIHGJKRSXYJMVUPGb@OPGz@Wx@Ur@Q`@Kd@OrAa@nCcAf@Op@Yj@Wj@Sx@YlAa@r@W|Ag@`@MzBs@xBq@z@Yf@OVK\\\\lAZfAZfAp@xBV`AV|@zD{Ap@YZMNGbAWjAe@dAa@^SZ[ZM\\\\Md@SnAg@nBw@x@pCl@vBd@`BPl@T`Ad@bBd@dBv@tCLd@`@rAL\\\\Lf@XhAXz@XbAHRJ^Vt@z@pCNj@V|@h@zBLXZfA`@tAd@nBTv@Tt@Rt@HVt@bCXfANh@V|@f@hBl@vBX~@Rt@x@vCv@nCV`A\\\\hAL`@N^FJTv@`@zAXdAXdAt@jCd@hBb@fBHVXfATv@V|@^xARv@Lb@HX\\\\nAf@rBNj@\",\"length\":308},\"routeShortName\":\"204\",\"routeLongName\":\"STA. CÂNDIDA / PINHEIRINHO\",\"rentedBike\":false,\"transitLeg\":true,\"duration\":1633.0,\"steps\":[]},{\"startTime\":1491251284000,\"endTime\":1491251570000,\"departureDelay\":0,\"arrivalDelay\":0,\"realTime\":false,\"distance\":373.245,\"pathway\":false,\"mode\":\"WALK\",\"route\":\"\",\"agencyTimeZoneOffset\":-10800000,\"interlineWithPreviousLeg\":false,\"from\":{\"name\":\"Estação Tubo Água Verde / Iguaçu\",\"stopId\":\"1:25740\",\"stopCode\":\"109003\",\"lon\":-49.285369401301,\"lat\":-25.448301917892,\"arrival\":1491251283000,\"departure\":1491251284000,\"stopIndex\":6,\"stopSequence\":7,\"vertexType\":\"TRANSIT\"},\"to\":{\"name\":\"Destination\",\"lon\":-49.28381,\"lat\":-25.45102,\"arrival\":1491251570000,\"orig\":\"\",\"vertexType\":\"NORMAL\"},\"legGeometry\":{\"points\":\"pjizCv`ykHQq@hIsCtFoB\",\"length\":4},\"rentedBike\":false,\"transitLeg\":false,\"duration\":286.0,\"steps\":[{\"distance\":27.478,\"relativeDirection\":\"DEPART\",\"streetName\":\"Avenida Iguaçu\",\"absoluteDirection\":\"EAST\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.2853946992316,\"lat\":-25.448246545446064,\"elevation\":[]},{\"distance\":345.767,\"relativeDirection\":\"RIGHT\",\"streetName\":\"Rua Doutor Alexandre Gutierrez\",\"absoluteDirection\":\"SOUTH\",\"stayOn\":false,\"area\":false,\"bogusName\":false,\"lon\":-49.285141700000004,\"lat\":-25.4481523,\"elevation\":[]}]}],\"tooSloped\":false}]},\"debugOutput\":{\"precalculationTime\":35,\"pathCalculationTime\":102,\"pathTimes\":[37,39,25],\"renderingTime\":4,\"totalTime\":141,\"timedOut\":false},\"elevationMetadata\":{\"ellipsoidToGeoidDifference\":3.6861111059479486,\"geoidElevation\":false}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Itinerary it = Itinerary.fromJson(itJson);
    }
}
