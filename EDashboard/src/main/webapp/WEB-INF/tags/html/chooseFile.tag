<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="label.action.choose" var="labelActionChoose"/>	

<div id="chooseFileDialog" title="${labelActionChoose}" class="dialog choose-dialog" style="display: none">		
	<div>
		<h3 class="float-left">
			<spring:message code="label.content.file.folder" />
		</h3>
		<div> 
	    	<table id="listFolder"><tr><td></td></tr></table> 
	    	<span id="pagerFolder"></span> 
	    </div> 
	</div>
	<div>		
		<h3 class="float-left">
			<spring:message code="label.content.file" />
		</h3>
		<div> 
	    	<table id="listFile"><tr><td></td></tr></table> 
	    	<span id="pagerFile"></span> 
	    </div> 
	</div>
</div>