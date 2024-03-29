<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<script type="text/javascript">
	var LogFactory = {
		<c:choose>
			<c:when test="<%= GetterUtil.getBoolean(PropsUtil.get(PropsUtil.JAVASCRIPT_LOG_ENABLED)) %>">
				appender : null,

				getLog : function(name) {
					var log;

					if (name == null || name == "") {
						name = "[default]";
					}

					log = log4javascript.getLogger(name);

					if (LogFactory.appender == null) {
						LogFactory.appender = new log4javascript.PopUpAppender(new log4javascript.PatternLayout("%d{HH:mm:ss} %-5p [%c] %m%n"));
	
						LogFactory.appender.setWidth(800);
						LogFactory.appender.setHeight(200);
					}

					log.addAppender(LogFactory.appender);

					return log;
				}
			</c:when>
			<c:otherwise>
				getLog : function(name) {
					return new LogFactory.DummyLogger();
				},

				DummyLogger : function () {
					this.trace = function(message, exception) {
					};

					this.debug = function(message, exception) {
					};

					this.info = function(message, exception) {
					};

					this.warn = function(message, exception) {
					};

					this.error = function(message, exception) {
					};

					this.fatal = function(message, exception) {
					};
				}
			</c:otherwise>
		</c:choose>
	}
</script>