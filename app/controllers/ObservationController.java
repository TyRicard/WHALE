package controllers;

import models.Observation;
import models.Whale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Scala.asScala;

/**
 * An example of form processing.
 *
 * https://playframework.com/documentation/latest/JavaForms
 * https://adrianhurt.github.io/play-bootstrap/
 */

public class ObservationController extends Controller {

    private final Form<ObservationData> form;
    private MessagesApi messagesApi;
    private final List<Observation> observations;
    private final ArrayList<Whale> Whales;

    private final Logger logger = LoggerFactory.getLogger(getClass()) ;

    @Inject
    public ObservationController(FormFactory formFactory, MessagesApi messagesApi) {
        this.form = formFactory.form(ObservationData.class);
        this.messagesApi = messagesApi;
        Whale w1 = new Whale( "Beluga", 204, "Male");
        Whale w2 = new Whale( "Orca", 111, "Female");
        Whale w3 = new Whale( "Blue", 301, "Male");
        this.Whales = new ArrayList<>();
        Whales.add(w1);
        Whales.add(w2);
        Whales.add(w3);
        this.observations = com.google.common.collect.Lists.newArrayList(
            new Observation(Whales, LocalDate.now().toString(), "1pm", "Canada, BC, Victoria")
        );
    }

    public Result index() {
        return ok(views.html.index.render());
    }

    public Result listObservations(Http.Request request) {
        return ok(views.html.listObservations.render(asScala(observations), form, request, messagesApi.preferred(request)));
    }

    public Result createObservation(Http.Request request) {
        final Form<ObservationData> boundForm = form.bindFromRequest(request);

        if (boundForm.hasErrors()) {
            logger.error("errors = {}", boundForm.errors());
            logger.error("boundForm.errors().size():"+boundForm.errors().size());
            for(play.data.validation.ValidationError err: boundForm.errors()){
                logger.error(err.toString());
            }
            logger.error("boundForm.toString():"+boundForm.toString());
            return badRequest(views.html.listObservations.render(asScala(observations), boundForm, request, messagesApi.preferred(request)));
        } else {
            ObservationData data = boundForm.get();
            ArrayList<Whale> whales = new ArrayList<>();
            int numWhales = data.getNumWhales();
            String weights = data.getWeights();
            String[] weigthsList = weights.split(",");
            for(int i = 0; i < numWhales; i++) {
                try {
                    whales.add(new Whale(data.getSpecies(), Integer.parseInt(weigthsList[i]), data.getGender()));
                } catch (Exception e) {
                    whales.add(new Whale(data.getSpecies(), 0, data.getGender()));
                }
            }
            observations.add(new Observation(whales, data.getDate(), data.getTime(), data.getLocation()));
            return redirect(routes.ObservationController.listObservations()).flashing("info", "Observation added!");
        }
    }

    public Result createWhale(Http.Request request) {
        final Form<ObservationData> boundForm = form.bindFromRequest(request);
        if (boundForm.hasErrors()) {
            logger.error("errors = {}", boundForm.errors());
            logger.error("boundForm.errors().size():"+boundForm.errors().size());
            for(play.data.validation.ValidationError err: boundForm.errors()){
                logger.error(err.toString());
            }
            logger.error("boundForm.toString():"+boundForm.toString());
            return badRequest(views.html.listObservations.render(asScala(observations), boundForm, request, messagesApi.preferred(request)));
        } else {
            WhaleData data = boundForm.get();
            System.out.println("data.getId():"+data.getId());
            Whales.add(new Whale(data.getSpecies(), data.getWeight(), data.getGender()));
            return redirect(routes.ObservationController.listObservations()).flashing("info", "Observation added!");
        }
    }

}

