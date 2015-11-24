package org.lemanoman.testeweb.restcontroller;
 
import org.lemanoman.testeweb.model.rest.BuscaModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

   
@RestController
public class StrategyController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public BuscaModel buscaCEPGet() {
	BuscaModel model  = new BuscaModel();
	model.setCep("Atwerrwer");
	return model;
    }

 
}