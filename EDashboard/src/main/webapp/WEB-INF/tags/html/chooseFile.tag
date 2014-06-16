<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="dialogId" required="true" rtexprvalue="true"%>
<%@ attribute name="listFolderId" required="true" rtexprvalue="true"%>
<%@ attribute name="pagerFolderId" required="true" rtexprvalue="true"%>
<%@ attribute name="listFileId" required="true" rtexprvalue="true"%>
<%@ attribute name="pagerFileId" required="true" rtexprvalue="true"%>
<%@ attribute name="selectMessageId" required="true" rtexprvalue="true"%>
<%@ attribute name="deselectId" required="true" rtexprvalue="true"%>
<%@ attribute name="messageId" required="true" rtexprvalue="true"%>
<%@ attribute name="title" required="false" rtexprvalue="true"%>
<%@ attribute name="titleFolder" required="false" rtexprvalue="true"%>
<%@ attribute name="titleFile" required="false" rtexprvalue="true"%>
<%@ attribute name="titleSelect" required="false" rtexprvalue="true"%>
<%@ attribute name="titleDeselectAll" required="false" rtexprvalue="true"%>

<div id="${dialogId}" title="${title}" class="dialog choose-dialog" style="display: none">		
	<div>
		<h3 class="float-left">
			${titleFolder}
		</h3>
		<div> 
	    	<table id="${listFolderId}"><tr><td></td></tr></table> 
	    	<span id="${pagerFolderId}"></span> 
	    </div> 
	</div>
	<div>		
		<div class="title-action">
			<h3 class="float-left">
				${titleFile}
			</h3>
			<div class="file-select float-left">(
				<span>${titleSelect}</span>:
				<span id="${selectMessageId}"></span> &nbsp; - &nbsp; 
				<a id="${deselectId}" href="javascript:" class="link-action" style="display: none;">${titleDeselectAll}</a>)
			</div>
			<div class="file-message float-right">
				<span id="${messageId}" style="display: none;">					
				</span>				
			</div>			
		</div>		
		<div> 
	    	<table id="${listFileId}"><tr><td></td></tr></table> 
	    	<span id="${pagerFileId}"></span> 
	    </div> 
	</div>
</div>