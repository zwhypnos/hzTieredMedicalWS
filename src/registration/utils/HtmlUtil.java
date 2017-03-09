package registration.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class HtmlUtil {
	/** 
     * ��html����תΪ�ı� 
     * @param html ��Ҫ�����html�ı� 
     * @param filterTags ��Ҫ������html��ǩ��ʽ 
     * @return 
     */  
    public static String trimHtml2Txt(String html, String[] filterTags){      
        html = html.replaceAll("\\<head>[\\s\\S]*?</head>(?i)", "");//ȥ��head  
        html = html.replaceAll("\\<!--[\\s\\S]*?-->", "");//ȥ��ע��  
        html = html.replaceAll("\\<![\\s\\S]*?>", "");  
        html = html.replaceAll("\\<style[^>]*>[\\s\\S]*?</style>(?i)", "");//ȥ����ʽ  
        html = html.replaceAll("\\<script[^>]*>[\\s\\S]*?</script>(?i)", "");//ȥ��js  
        html = html.replaceAll("\\<w:[^>]+>[\\s\\S]*?</w:[^>]+>(?i)", "");//ȥ��word��ǩ  
        html = html.replaceAll("\\<xml>[\\s\\S]*?</xml>(?i)", "");  
        html = html.replaceAll("\\<html[^>]*>|<body[^>]*>|</html>|</body>(?i)", "");  
        html = html.replaceAll("\\\r\n|\n|\r", " ");//ȥ������  
        html = html.replaceAll("\\<br[^>]*>(?i)", "\n");  
        List<String> tags = new ArrayList<String>();  
        List<String> s_tags = new ArrayList<String>();  
        List<String> halfTag = Arrays.asList(new String[]{"img","table","thead","th","tr","td"});//  
        if(filterTags != null && filterTags.length > 0){  
            for (String tag : filterTags) {  
                tags.add("<"+tag+(halfTag.contains(tag)?"":">"));//��ʼ��ǩ  
                if(!"img".equals(tag)) tags.add("</"+tag+">");//������ǩ  
                s_tags.add("#REPLACETAG"+tag+(halfTag.contains(tag)?"":"REPLACETAG#"));//�����滻Ϊ����һ��ı��,��������ʾ�ı����,�磺�ı��а���#td��#table��  
                if(!"img".equals(tag)) s_tags.add("#REPLACETAG/"+tag+"REPLACETAG#");  
            }  
        }  
        html = StringUtils.replaceEach(html, tags.toArray(new String[tags.size()]), s_tags.toArray(new String[s_tags.size()]));                 
        html = html.replaceAll("\\</p>(?i)", "\n");  
        html = html.replaceAll("\\<[^>]+>", "");  
        html = StringUtils.replaceEach(html,s_tags.toArray(new String[s_tags.size()]),tags.toArray(new String[tags.size()]));  
        html = html.replaceAll("\\ ", " ");  
        return html.trim();  
    }  
}
