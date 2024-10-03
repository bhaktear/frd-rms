package abl.frd.qremit.converter.nafex.controller;

import abl.frd.qremit.converter.nafex.helper.MyUserDetails;
import abl.frd.qremit.converter.nafex.model.FileInfoModel;
import abl.frd.qremit.converter.nafex.model.User;
import abl.frd.qremit.converter.nafex.service.AgexSingaporeModelService;
import abl.frd.qremit.converter.nafex.service.CommonService;
import abl.frd.qremit.converter.nafex.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AgexSingaporeModelController {
    private final MyUserDetailsService myUserDetailsService;
    private final AgexSingaporeModelService agexSingaporeModelService;
    private final CommonService commonService;
    @Autowired
    public AgexSingaporeModelController(AgexSingaporeModelService agexSingaporeModelService, MyUserDetailsService myUserDetailsService, CommonService commonService ){
        this.myUserDetailsService = myUserDetailsService;
        this.agexSingaporeModelService = agexSingaporeModelService;
        this.commonService = commonService;
    }
    @PostMapping("/singaporeUpload")
    public String uploadFile(@AuthenticationPrincipal MyUserDetails userDetails, @ModelAttribute("file") MultipartFile file, 
        @ModelAttribute("fileType") String fileType, @ModelAttribute("exchangeCode") String exchangeCode, Model model) {
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
        FileInfoModel fileInfoModelObject;
        if (CommonService.hasCSVFormat(file)) {
            if(!commonService.ifFileExist(file.getOriginalFilename())){
                try {
                    fileInfoModelObject = agexSingaporeModelService.save(file, userId, exchangeCode,fileType);
                    if(fileInfoModelObject!=null){
                        model.addAttribute("fileInfo", fileInfoModelObject);
                        return CommonService.uploadSuccesPage;
                    }
                    else{
                        message = "All Data From Your Selected File Already Exists!";
                        model.addAttribute("message", message);
                        return CommonService.uploadSuccesPage;
                    }
                }
                catch (IllegalArgumentException e) {
                    message = e.getMessage();
                    model.addAttribute("message", message);
                    return CommonService.uploadSuccesPage;
                }
                catch (Exception e) {
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
