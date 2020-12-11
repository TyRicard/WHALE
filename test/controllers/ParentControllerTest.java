package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import static play.test.Helpers.route;

/**
 * Code based off
 * https://github.com/playframework/play-samples/blob/2.8.x/play-java-rest-api-example/
 */

public class ParentControllerTest extends WithApplication {


    private JsonNode contentAsJson(Result result) {
        final String responseBody = contentAsString(result);
        return Json.parse(responseBody);
    }

    @Override
    protected Application provideApplication(){
        return new GuiceApplicationBuilder().build();       //Create application for tests to run on
    }


    //      GET     /                           controllers.ParentController.index
    @Test
    public void indexTest() {

        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri("/");     //Build request for index() route
        Result result = route(app,request);                                                 //Route request to app
        assertEquals(OK, result.status());                                                  //Assert status matches
        assertEquals("text/html", result.contentType().get());                      //Assert mime type matches
        assertEquals("utf-8", result.charset().get());                              //Assert charset matches
    }

    //    POST    /Whales                    controllers.ParentController.createWhale(request: Request)
    @Test
    public void createWhaleTest() {
        Http.RequestBuilder request = Helpers.fakeRequest()                                 //Build request for createWhale() route
                .method("POST")
                .bodyForm(ImmutableMap.of("species","Orca","weight","2200","gender","F"))   //Add fields for form
                .uri("/Whales");
        Result result = route(app,request);                                                 //Send to app
        assertEquals(SEE_OTHER, result.status());                                           //Assert HTTP return is correct
    }

    //    GET     /Whales                    controllers.ParentController.listWhales(request: Request)
    @Test
    public void listWhalesTest() {                                                               //Test list whales method
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri("/Whales");   //Build request for createWhale() route
        Result result = route(app,request);                                                     //Send to app
        assertEquals(OK, result.status());                                                      //Assert HTTP response matches
        assertEquals("text/html", result.contentType().get());                          //Assert mime type matches
        assertEquals("utf-8", result.charset().get());                                  //Assert charset matches
    }

    //    GET     /Whales/getWhales          controllers.ParentController.getWhales(request: Request)
    @Test
    public void getWhalesTest() {                                                                       //Test get Whales method
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri("/Whales/getWhales"); //Build request for createWhale() route
        Result result = route(app,request);                                                             //Send to app
        assertEquals(OK, result.status());                                                              //Assert HTTP response matches
        assertEquals("text/html", result.contentType().get());                                  //Assert mime type matches
        assertEquals("utf-8", result.charset().get());                                          //Assert charset matches
    }


//    POST    /Whales/filter             controllers.ParentController.filterWhales(request: Request)
//    GET     /Whales/filter             controllers.ParentController.listFilterWhales(request: Request)
//    POST    /Whales/filter/removeFilter       controllers.ParentController.removeWhaleFilter()
    @Test
    public void whaleFilterTest() {                                                 //Test whale filter process. Add whale filter, get filtered list, remove filter
        Http.RequestBuilder request1 = Helpers.fakeRequest()
                .method("POST")
                .bodyForm(ImmutableMap.of("species","Orca"))                //Create request to filter by Orca
                .uri("/Whales/filter");
        Result result = route(app,request1);
        assertEquals(SEE_OTHER, result.status());                                   //Assert filter route is successful

        Http.RequestBuilder request2 = new Http.RequestBuilder().method("GET").uri("/Whales/filter");   //Create request for getting filtered list
        result = route(app,request2);
        assertEquals(OK, result.status());                                                              //Assert correct HTTP response, mime type, and charset
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());

        Http.RequestBuilder request3 = Helpers.fakeRequest()                        //Create request remove filters
                .method("POST")
                .uri("/Whales/filter/removeFilter");
        result = route(app,request3);
        assertEquals(SEE_OTHER, result.status());                                   //Assert correct HTTP response correct
    }


    //GET     /observations                    controllers.ParentController.listObservations(request: Request)
    @Test
    public void listObservationsTest(){                                                             //Test listObservations method
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri("/observations");     //Build request
        Result result = route(app,request);
        assertEquals(OK, result.status());                                                              //Assert Http response, mime type, and charset match
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
    }

    //POST    /observations                    controllers.ParentController.createObservation(request: Request)
    @Test
    public void createObservationTest(){                                                            //Test for creating an Observation
        //API call to get the id of a whale in whales table
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri("/Whales/getWhales").header("Accept","application/txt+json");
        Result result = route(app,request);
        String id = contentAsJson(result).get("body").get(0).get("id").toString();  //convert json api response to the first whale's id

        request = Helpers.fakeRequest()                                                             //build request for new observation
                .method("POST")
                .bodyForm(ImmutableMap.of("whaleIDList",id,"location","Victoria BC Canada","date","2020-12-10","time","1pm"))
                .uri("/observations");
        result = route(app,request);
        assertEquals(SEE_OTHER, result.status());                                                     //Assert correct Http response
    }


//POST    /observations/filter             controllers.ParentController.filterObservations(request: Request)
//GET     /observations/filter             controllers.ParentController.listFilteredObservations(request: Request)
//POST    /observations/filter/removeFilter       controllers.ParentController.removeObservationFilter()
    @Test
    public void observationFilterTest() {                                                           //Test observation filter process. Create filter, get filtered list, remove filter
        Http.RequestBuilder request1 = Helpers.fakeRequest()                                        //Create request for filter
                .method("POST")
                .bodyForm(ImmutableMap.of("date","2020-12-01"))
                .uri("/observations/filter");
        Result result = route(app,request1);
        assertEquals(SEE_OTHER, result.status());                                                                   //Assert Http success

        Http.RequestBuilder request2 = new Http.RequestBuilder().method("GET").uri("/observations/filter");         //Create request to get filtered list
        result = route(app,request2);
        assertEquals(OK, result.status());                                                                          //Assert Http success
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());

        Http.RequestBuilder request3 = Helpers.fakeRequest()                                                        //Create requets to remove filter
                .method("POST")
                .uri("/observations/filter/removeFilter");
        result = route(app,request3);
        assertEquals(SEE_OTHER, result.status());                                                                   //Assert Http success
    }

//GET     /Stats                      controllers.ParentController.stats
@Test
    public void statsTest(){                                                                            //Test route to stats page
        Http.RequestBuilder request = new Http.RequestBuilder().method("GET").uri("/Stats");            //Create request
        Result result = route(app,request);
        assertEquals(OK, result.status());                                                              //Assert correct Http response, mime type, charset
        assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
    }
}

