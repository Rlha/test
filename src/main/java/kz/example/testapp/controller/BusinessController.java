package kz.example.testapp.controller;

import kz.example.testapp.model.Business;
import kz.example.testapp.repository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class BusinessController {

    @Autowired
    private BusinessRepository businessRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Business> findItems(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return businessRepository.findAll(new PageRequest(page - 1, size));
    }

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public Long countItems() {
        return businessRepository.count();
    }

    @RequestMapping(value = "/by-description", method = RequestMethod.GET)
    public Page<Business> findByDescription(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "") String descriptionPart) {
        return businessRepository.findByDescriptionContaining(descriptionPart, new PageRequest(page - 1, size));
    }

    @RequestMapping(value = "/by-status", method = RequestMethod.GET)
    public Page<Business> findByBusinessStatus(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "DONE") Business.BusinessStatus businessStatus) {
        return businessRepository.findByBusinessStatus(businessStatus, new PageRequest(page - 1, size));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Business addBusiness(@RequestBody Business business) {
        business.setId(null);
        return businessRepository.saveAndFlush(business);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Business updateBusiness(@RequestBody Business updatedBusiness, @PathVariable Integer id) {
        updatedBusiness.setId(id);
        return businessRepository.saveAndFlush(updatedBusiness);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBusiness(@PathVariable Integer id) {
        businessRepository.delete(id);
    }

}
