package abl.frd.qremit.converter.controller;

import abl.frd.qremit.converter.helper.MyUserDetails;
import abl.frd.qremit.converter.model.User;
import abl.frd.qremit.converter.service.CommonService;
import abl.frd.qremit.converter.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import abl.frd.qremit.converter.service.NecItalyModelService;
import java.util.*;

@Controller
public class NecItalyModelController {
    private final MyUserDetailsService myUserDetailsService;
    private final NecItalyModelService necItalyModelService;
    private final CommonService commonService;
    
    @Autowired
    public NecItalyModelController(NecItalyModelService necItalyModelService,MyUserDetailsService myUserDetailsService, CommonService commonService){
        this.myUserDetailsService = myUserDetailsService;
        this.necItalyModelService = necItalyModelService;
        this.commonService = commonService;
    }
    
    @PostMapping("/necitalyUpload")
    public String uploadFile(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute("file") MultipartFile file, @ModelAttribute("exchangeCode") String exchangeCode,
        @RequestParam("nrtaCode") String nrtaCode, @RequestParam("tbl") String tbl, Model model) {
        model.addAttribute("exchangeMap", myUserDetailsService.getLoggedInUserMenu(userDetails));  
        int userId = 000000000;
        // Getting Logged In user Details in this block
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
            User user = myUserDetails.getUser();
            userId = user.getId();
        }
        String message = "";
        if (CommonService.hasCSVFormat(file)) {
            if(!commonService.ifFileExist(file.getOriginalFilename())) {
                try {
                    Map<String, Object> resp = necItalyModelService.save(file, userId, exchangeCode, nrtaCode, tbl);
                    model = CommonService.viewUploadStatus(resp, model);
                    return CommonService.uploadSuccesPage;
                }catch (IllegalArgumentException e) {
                    model.addAttribute("message", e.getMessage());
                    return CommonService.uploadSuccesPage;
                }catch (Exception e) {
                    message = "Could Not Upload The File: " + file.getOriginalFilename() +"";
                    model.addAttribute("message", message);
                    return CommonService.uploadSuccesPage;
                }
            }
            message = "File With The Name "+ file.getOriginalFilename() +" Already Exists !!";
            model.addAttribute("message", message);
            return CommonService.uploadSuccesPage;
        }
        message = "Please Upload a CSV File!";
        model.addAttribute("message", message);
        return CommonService.uploadSuccesPage;
    }
}
