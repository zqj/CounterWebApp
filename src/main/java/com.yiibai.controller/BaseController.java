package com.yiibai.controller;

import com.alibaba.fastjson.JSONArray;
import com.yiibai.util.ExcelUtils;
import com.yiibai.util.FileUploadingUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BaseController {

    private static int counter = 0;
    private static final String VIEW_INDEX = "index";
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BaseController.class);

    //通过Spring的autowired注解获取spring默认配置的request
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {

        model.addAttribute("message", "Welcome");
        model.addAttribute("counter", ++counter);
        logger.debug("[welcome] counter : {}", counter);

        // Spring uses InternalResourceViewResolver and return back index.jsp
        return VIEW_INDEX;

    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute("message", "Welcome " + name);
        model.addAttribute("counter", ++counter);
        logger.debug("[welcomeName] counter : {}", counter);
        return VIEW_INDEX;

    }

    /***
     * 保存文件
     * @param file
     * @return
     */
    private boolean saveFile(MultipartFile file) {
        // 判断文件是否为空
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                //String filePath = request.getSession().getServletContext().getRealPath("\\") + "upload\\" + file.getOriginalFilename(); // "/"
                String filePath = "D:\\upload\\" + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @RequestMapping(value = "/filesUpload.do", method = RequestMethod.POST)
    public ModelAndView  filesUpload(@RequestParam(value="files",required=true) MultipartFile[] files, ModelMap model) throws IOException {
        //判断file数组不能为空并且长度大于0
        String show = "s111";
        model.addAttribute("show",show);
        if(files!=null&&files.length>0){
            //循环获取file数组中得文件
            Map<String,MultipartFile> map = new HashMap<String,MultipartFile>();
            for(int i = 0;i<files.length;i++){
                map.put(files[i].getOriginalFilename(),files[i]);
                //保存文件
                //saveFile(file);
            }
            FileUploadingUtil.FILEDIR = "D:\\upload\\";
            Map<String, String> result = FileUploadingUtil.upload(map);
            model.addAttribute("result",result);
        }
        // 重定向
        return new ModelAndView("list");
        //return "redirect:/list.do";
    }

    @RequestMapping(value = "/fileResovel.do", method = RequestMethod.POST)
    public ModelAndView  fileResovel(@RequestParam(value="file",required=true) MultipartFile file, ModelMap model) throws IOException {
        //判断file数组不能为空并且长度大于0
        if(file!=null){
            //循环获取file数组中得文件
            Map<String,MultipartFile> map = new HashMap<String,MultipartFile>();
            map.put(file.getOriginalFilename(),file);

            FileUploadingUtil.FILEDIR = "D:\\upload\\";
            Map<String, String> result = FileUploadingUtil.upload(map);
            model.addAttribute("result",result);
            String[] names = {"name","mobile"};
            for(String path : result.values()) {
                JSONArray json = ExcelUtils.exportToJSON(new File(path),names);
                model.addAttribute("json",json.toJSONString());
                break;
            }

        }
        // 重定向
        return new ModelAndView("list");
    }

    @RequestMapping(value = "/downloadExcel.do", method = RequestMethod.GET)
    public String  downloadExcel(ModelMap model) throws IOException {
        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
        for(int i = 0;i < 10;i++) {
            Map<String,Object> d = new HashMap<String, Object>();
            d.put("name", "N" +  i);
            d.put("mobile", "1897878909" + i);
            listData.add(d);
        }

        List<String> columns = new ArrayList<String>();

        columns.add("name");
        columns.add("mobile");

        ExcelUtils.exportExcel(response, "test",  listData, "test1", columns);

        return null;
    }
    /***
     * 读取上传文件中得所有文件并返回
     *
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list() {
        //String filePath = request.getSession().getServletContext().getRealPath("\\") + "upload\\";
        String filePath = "D:\\upload\\" ;
        System.out.println(filePath);
        ModelAndView mav = new ModelAndView("list");
        File uploadDest = new File(filePath);
        String[] fileNames = uploadDest.list();
        for (int i = 0; i < fileNames.length; i++) {
            //打印出文件名
            System.out.println(fileNames[i]);
        }
        return mav;
    }


}