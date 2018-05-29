package com.mouchina.web.base.filter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.mouchina.base.utils.SensitiveWordUtil;


/**
 * 敏感词过滤filter
 * @author Administrator
 *
 */
public class SensitiveWordFilter implements Filter{

	Logger logger = Logger.getLogger(SensitiveWordFilter.class);
	
	Set<String> excludeUrl = new HashSet<>();  
	
    Pattern _pattenContentType;  
    
    boolean _enbale = true;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		
		String url = req.getRequestURI();  
        //1.1 不验证的资源  
        if (!excludeUrl.contains(url)) {  
        	chain.doFilter(request, response);  
            return;  
        }else{
        	try {
        		
        		if(SensitiveWordUtil.sensitiveWordMap != null){
        			logger.info("-----------------过滤敏感词----------------------");
    				response.reset();
    				ResponseReplaceWrapper responseWrapper = new ResponseReplaceWrapper(rep);
    				chain.doFilter(request, responseWrapper);
    				String content = responseWrapper.getTextContent();
    				//System.out.println("原始内容：" + content);
    				/*Pattern p = Pattern.compile("\n|\r");
    	            Matcher m = p.matcher(content);
    	            content = m.replaceAll("");*/
    				String result = SensitiveWordUtil.replaceSensitiveWord(content,SensitiveWordUtil.MaxMatchType);
    				//System.out.println("过滤后内容：" + result);
    				response.getWriter().write(result);
    				response.flushBuffer();
        		}else{
        			chain.doFilter(request, response);
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }      
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String excludeUrl_ = config.getInitParameter("excludeUrl");
		if(StringUtils.isNotBlank(excludeUrl_)){
			String[] urls = excludeUrl_.split(",");
			if(urls != null && urls.length > 0){
				for(String url : urls){
					System.out.println(url);
					excludeUrl.add(url);
				}
			}
		}
	}
	
	class ResponseReplaceWrapper extends HttpServletResponseWrapper {
		
		private CharArrayWriter charArrayWriter = new CharArrayWriter();
		
		private ByteArrayOutputStream stream = new ByteArrayOutputStream();

	    public ResponseReplaceWrapper(HttpServletResponse response) {
	        super(response);
	    }

	    @Override
	    public PrintWriter getWriter() throws IOException {
	        return new PrintWriter(charArrayWriter);
	    }
	    
	    public void flush(){  
	        try {  
	        	charArrayWriter.flush();  
	        	charArrayWriter.close();  
	            stream.flush();  
	            stream.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    
	    @Override
	    public ServletOutputStream getOutputStream() throws IOException {
	    	return new ServletOutputStream(){  
	            @Override  
	            public void write(int b) throws IOException {  
	                stream.write(b);  
	            }  
	        };  
	    }

	    public CharArrayWriter getCharWriter(){
	        return charArrayWriter;
	    }
	    
	    public ByteArrayOutputStream getByteArrayOutputStream(){  
	        return stream;  
	    } 
	    
	    public String getTextContent() throws UnsupportedEncodingException {  
	        flush(); 
	        byte[] lens = stream.toByteArray();
	        return new String(lens,"utf-8");  
	    }  
    }
}
