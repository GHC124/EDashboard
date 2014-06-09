<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="label.actions" var="labelActions"/>
<spring:message code="label.action.choose" var="labelActionChoose"/>
<spring:message code="label.content.file.folder.name" var="labelFolderName"/>
<spring:message code="label.content.file.folder.description" var="labelFolderDescription"/>
<spring:message code="label.content.file.name" var="labelFileName"/>
<spring:message code="label.content.file.description" var="labelFileDescription"/>
<spring:message code="label.content.file.size" var="labelFileSize"/>
<spring:message code="label.content.file.date_up" var="labelFileDateUp"/>
<spring:message code="label.action.save" var="labelActionSave"/>
<spring:message code="label.action.close" var="labelActionClose"/>
<spring:url value="/secured/content/file" var="showFileUrl"/>	

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