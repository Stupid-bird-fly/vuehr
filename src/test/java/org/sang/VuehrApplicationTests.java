package org.sang;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sang.common.XmlUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.Document;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VuehrApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Test
    public void test2() throws Exception {
        String result = null;
        try {
            result = new String("\"1123\",'123',1,1.1,2.1,true,false,{\"d\":11},[1,2,3,4,5],${return '1'},<xml>12312</xml>,ere2342qweqwe1".getBytes(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] s1=result.split(",|\\[|\\]");
        List<String> r1=new ArrayList<>();
        List<Long> r5=new ArrayList<>();
        List<Double> r6=new ArrayList<>();
        List<String> r7=new ArrayList<>();
        boolean r2=false;
        boolean r3=false;
        JSONObject jsonObject=null;
        String r4=result.substring(result.indexOf("["),result.indexOf("]")+1);
        //jsonObject=JSONObject.fromObject(r4);
        for(int i=0;i<s1.length;i++){
           // System.out.println(s1[i]);
            if(s1[i].startsWith("'")&&s1[i].endsWith("'")||s1[i].startsWith("\"")&&s1[i].endsWith("\"")){
                r1.add(s1[i]);
                //System.out.println(s1[i]);
            }else if(s1[i].startsWith("{")&&s1[i].endsWith("}")||s1[i].startsWith("[")&&s1[i].endsWith("]")){
                jsonObject= JSONObject.fromObject(s1[i]);
                //System.out.println(s1[i]);
            }else if(s1[i].startsWith("${")&&s1[i].endsWith("}")){
                //System.out.println(s1[i]);
            }else if(s1[i].contains("true")){
                //r2=Boolean.valueOf(s1[i]);
            }else if(s1[i].contains("false")){
                //r3=Boolean.valueOf(s1[i]);
            }else if (s1[i].startsWith("<xml>")&&s1[i].endsWith("</xml>")){
                XmlUtil xmlUtil=new XmlUtil();
                try {
                    xmlUtil.strChangeXML(s1[i]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //System.out.println(s1[i]);
            }//Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            else if (!s1[i].contains(".")&& StringUtils.isNumeric(s1[i])){
                System.out.println(s1[i]);
                //r5.add(Long.valueOf(s1[i]));
            }else if (s1[i].contains(".")){
                r6.add(Double.valueOf(s1[i]));
                System.out.println(s1[i]);
            }else if(s1[i].replaceAll("[a-z]*[A-Z]*\\d*\\s*", "").length()==0){
                System.out.println(s1[i]);
                r7.add(s1[i]);
            }else{
                System.out.println(s1[i]);
                throw new Exception("该字符暂无解析方案");
            }
        }
    }
}
