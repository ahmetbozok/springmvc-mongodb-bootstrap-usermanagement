package com.springmvc.usermanagement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.usermanagement.exception.UserExistException;
import com.springmvc.usermanagement.model.AjaxCallResponse;
import com.springmvc.usermanagement.model.ErrorMessage;
import com.springmvc.usermanagement.model.FormValidationAttribute;
import com.springmvc.usermanagement.model.User;
import com.springmvc.usermanagement.model.ValidationResponse;
import com.springmvc.usermanagement.service.DataService;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

@Controller
public class UserManagementController {
	static final Logger logger = Logger.getLogger(UserManagementController.class);
    @Autowired
    private ReCaptcha reCaptchaService = null;
	@Autowired
	DataService dataService;

	/*
	 * This method will run when application start first time and it redirect /users
	*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		logger.debug("index()");
		return "redirect:/users";
	}

	/*
	 * This method returns user list
	*/
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getUserList(ModelMap model) {
		logger.debug("getUserList()");
		List<User> users = null;
		try {
			users = dataService.getUserList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("An error has occured in getUserList(). Detail :", e);
		}
		model.addAttribute("users", users);
		return "userlist";
	}
	
	/*
	 * This method create a new User object to open a modal dialog for user adding form
	*/
	@RequestMapping(value = "/users/newUser", method = RequestMethod.GET)
	public String createNewUser(Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("createNewUser()");
		redirectAttributes.addFlashAttribute("selectedUser", new User());

		return "redirect:/users";
	}

	/*
	 * This method will run after form validation to add new user object
	*/
	@RequestMapping(value = "/users/newUser/save", method = RequestMethod.POST)
	public String saveNewUser(@ModelAttribute("selectedUser") @Validated User user, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.debug("saveNewUser()");
		try {
			dataService.addUser(user);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "User added successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(e instanceof UserExistException) {
				logger.debug("This user name is taken! (userName: " + user.getUserName() + ")");
				redirectAttributes.addFlashAttribute("msg", "This user name is taken! Please try another user name!");
			} else {
				logger.error("An error has occured in saveNewUser(). Detail :", e);
				redirectAttributes.addFlashAttribute("msg", "Sorry, an error has occur! User not added!");
			}
			redirectAttributes.addFlashAttribute("css", "error");
		}

		return "redirect:/users";
	}

	/*
	 * This method will run to load selected record from user list. And then open a modal dialog window for edit user form
	*/
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public String loadSelectedUserToUpdate(@PathVariable("id") String id, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("loadSelectedUserToUpdate()");
		User user = null;
		if (!StringUtils.isEmpty(id)) {
			try {
				user = dataService.getUserById(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("An error has occured in loadSelectedUserToUpdate(). Detail :", e);
				redirectAttributes.addFlashAttribute("css", "error");
				redirectAttributes.addFlashAttribute("msg", "Sorry, an error has occur!");
			}
		}
		if (user == null) {
			logger.debug("User not found with user id! (UserId : "+ id +")");
			redirectAttributes.addFlashAttribute("css", "error");
			redirectAttributes.addFlashAttribute("msg", "User not found!");
		}
		redirectAttributes.addFlashAttribute("selectedUser", user);

		// POST/REDIRECT/GET
		return "redirect:/users";
	}

	/*
	 * This method will run after form validation to update selected user
	*/
	@RequestMapping(value = "/users/{id}/update", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("selectedUser") @Validated User user, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {
		logger.debug("updateUser()");
		try {
			dataService.updateUser(user);
			logger.debug("User updated successfully! (Username: "+ user.getUserName() +")");
			redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
			redirectAttributes.addFlashAttribute("css", "success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("An error has occured in updateUser(). Detail :", e);
			redirectAttributes.addFlashAttribute("msg", "Sorry, an error has occur! User not updated!");
			redirectAttributes.addFlashAttribute("css", "error");
		}

		// POST/REDIRECT/GET
		return "redirect:/users";
	}

	/*
	 * This method will run to delete selected user from user list after click delete button. It is an ajax call
	*/
	@RequestMapping(value = "/users/{id}/delete", method = RequestMethod.DELETE)
	public @ResponseBody AjaxCallResponse deleteUser(@PathVariable("id") String id, Model model) {
		logger.debug("deleteUser()");
		AjaxCallResponse response = null;
		try {
			User user = dataService.getUserById(id);
			if (user != null) {
				dataService.deleteUser(user);
				logger.debug("User deleted successfully! (UserName: "+ user.getUserName() +")");
				response = new AjaxCallResponse(FormValidationAttribute.SUCCESS.name(), "User deleted successfully!");
			} else {
				logger.debug("User not found! (UserId: "+ id +")");
				response = new AjaxCallResponse(FormValidationAttribute.FAIL.name(), "User not found!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("An error has occured in deleteUser(). Detail :", e);
			response = new AjaxCallResponse(FormValidationAttribute.FAIL.name(),
					"Sorry, an error has occur! User not deleted!");
		}

		return response;
	}

	/*
	 * This method will run to validate user form for update or add new user. It is an ajax call
	*/
	@RequestMapping(value = "/userFormValidation.json", method = RequestMethod.POST)
	public @ResponseBody ValidationResponse validateUserFormWithAjaxCall(ServletRequest request,
			@ModelAttribute(value = "selectedUser") @Valid User user, BindingResult result) {
		logger.debug("validateUserFormWithAjaxCall()");
		ValidationResponse res = new ValidationResponse();

        //isCaptchaValid is setted true at first because google api want to register for site to show captcha
        boolean isCaptchaValid = true;
        try {
            String challenge = request.getParameter("recaptcha_challenge_field");
            String response = request.getParameter("recaptcha_response_field");
            String remoteAddr = request.getRemoteAddr();
            ReCaptchaResponse reCaptchaResponse = reCaptchaService.checkAnswer(remoteAddr, challenge, response);
            if(reCaptchaResponse.isValid()) {
            	isCaptchaValid = true;
            } else {
            	isCaptchaValid = false;
            }
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("An error has occured when captcha validating. Detail :", e);
		}
        
		if (!result.hasErrors() && isCaptchaValid) {
			res.setStatus(FormValidationAttribute.SUCCESS.name());
		} else {
			res.setStatus(FormValidationAttribute.FAIL.name());
			List<FieldError> allErrors = result.getFieldErrors();
			List<ErrorMessage> errorMesages = new ArrayList<ErrorMessage>();
			String fieldError = "" ;
			for (FieldError objectError : allErrors) {
				errorMesages.add(new ErrorMessage(objectError.getField(),
						objectError.getField() + "  " + objectError.getDefaultMessage()));
				fieldError += objectError.getField() + " " + objectError.getDefaultMessage() + ", ";
			}
			logger.debug(fieldError);
			res.setErrorMessageList(errorMesages);
		}

		return res;
	}
}
