package com.prash.servlet.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Java Servlet Filter to check host and URI combination to avoid different portal pages from different domains which are not applicable 
 * Recommemded to configure this filter to default URL mapping (i.e., /*)
 * 
 * @author prashanth
 *
 */

public class HostURIFilter implements Filter {

	private static final String URIPREFIX = "/(.*?)/";
	private static Pattern p;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		p = Pattern.compile(URIPREFIX);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String host = checkAndExtractHost(httpRequest.getHeader("host"));
		String uri = getURIFirstMatch(httpRequest.getRequestURI());

		boolean validCom = HostURIEnum.validateHostWithURIOnlyIfHostMatch(host, uri);
		System.out.println("host:uri " + host + ":" + uri + " isValid " + validCom);

		if (validCom) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.getWriter().write("a different response... e.g in HTML");
	}

	private String getURIFirstMatch(String uri) {
		Matcher m = p.matcher(uri);
		while (m.find()) {
			uri = m.group();
		}
		return uri;
	}

	private String checkAndExtractHost(String host) {
		if (host == null) {
			return host;
		}
		int idx = host.indexOf(":");
		if (idx > 0) {
			host = host.substring(0, idx);
		}
		return host;
	}

	@Override
	public void destroy() {
	}

	
	/**
	 * Add host uri combination as new entry as enum below only if host check is needed. For DEV and UAT since we support multiple uris, so need not add any entry
	 * remember to add the forward slashes at starting and begining if adding a new combination below, otherwise REGEX won't match
	 * 
	 * @author prashanth
	 * 
	 */
	
	enum HostURIEnum {

		
	
		PRODEMS1("myems.emspay.eu", "/emsMerchantUI/"), 
		PRODEMS2("myems.emscard.com", "/emsMerchantUI/"), 
		PRODFDP("polcard.oneportal24.com", "/fdpMerchantUI/");

		String host;
		String uri;

		static Map<String, List<String>> hostURIMap;

		static {
			hostURIMap = Arrays.stream(HostURIEnum.values()).collect(Collectors.groupingBy(k -> k.host, Collectors.mapping(v -> v.uri, Collectors.toList())));
		}

		private HostURIEnum(String host, String uri) {
			this.host = host;
			this.uri = uri;
		}

		public static boolean validateHostWithURIOnlyIfHostMatch(String paramHost, String paramUri) {
			if (hostURIMap.containsKey(paramHost)) {
				return hostURIMap.get(paramHost).contains(paramUri);
			}
			return true;
		}
	}

	public static void main(String[] args) {
		String uri = "/sample/api/models/4";
		p = Pattern.compile(URIPREFIX);
		Matcher m = p.matcher(uri);
		while (m.find()) {
			System.out.println(m.group());
			System.out.println(m.regionStart());
			System.out.println(m.regionEnd());
			System.out.println(m.groupCount());
		}
	}
}