function chooseFileDialog(){
	this.showFileUrl='';
	this.labelFolderName='';
	this.labelFolderDescription='';
	this.labelActions='';
	this.labelFileName='';
	this.labelFileDescription='';
	this.labelFileSize='';
	this.labelFileDateUp='';
	this.labelActionSave='';
	this.labelActionClose='';
	this.globalSelectedFolderId='';
	this.listFolderId='';
	this.listFileId='';
	this.pagerFolderId='';
	this.pagerFileId='';	
	this.initLabel = function(){
		
	};
	this.initStructure = function(){
		var current = this;
		$("#" + this.listFolderId).jqGrid({ 
		    url: current.showFileUrl + '/folders', 
		    datatype: 'local', 
		    mtype: 'GET', 
		    colNames:[ current.labelFolderName, current.labelFolderDescription, current.labelActions], 
		    colModel :[  
		      {name:'name', index:'name', width:150},
		      {name:'description', index:'description', sortable:false, width:150},
		      {name:'act', index:'act', sortable:false, width:25}
		    ], 
		    jsonReader : { 
		        root:"data", 
		        page: "currentPage", 
		        total: "totalPages", 
		        records: "totalRecords", 
		        repeatitems: false, 
		        id: "id" 
		    },       
		    pager: '#' + current.pagerFolderId, 
		    rowNum:5, 
		    rowList:[5,10,20,30,50], 
		    sortname: 'name', 
		    sortorder: 'asc', 
		    viewrecords: true, 
		    gridview: true, 
		    height: 150, 
		    width: 700, 
		    beforeRequest: function(){
		    	
		    	//clearJQGridData($('#listFile'), {page : 1});
		    },
		    onSelectRow: function(id){ 
		    	globalSelectedFolderId = id;		    	
		    	refreshJQGrid('#' + current.listFileId, { page: 1, postData: {folderId:id}, datatype: "json" });
		    },
		    loadComplete: function() {	
		    	var grid = $(this);
		        
		    }
		  });	    	
		$("#" + this.listFileId).jqGrid({ 
		  url: current.showFileUrl + '/listFile', 
		  datatype: 'local', 
		  mtype: 'GET', 
		  colNames:[ current.labelFileName, current.labelFileDescription, current.labelFileSize, current.labelFileDateUp], 
		  colModel :[  
		    {name:'name', index:'name', width:150},
		    {name:'description', index:'description', sortable:false, width:150},
		    {name:'sizeString', index:'size', width:50},
		    {name:'dateUpString', index:'dateUp', width:50}
		  ], 
		  jsonReader : { 
		      root:"data", 
		      page: "currentPage", 
		      total: "totalPages", 
		      records: "totalRecords", 
		      repeatitems: false, 
		      id: "id" 
		  },       
		  pager: '#' + current.pagerFileId, 
		  rowNum:5, 
		  rowList:[5,10,20,30,50], 
		  sortname: 'name', 
		  sortorder: 'asc', 
		  viewrecords: true, 
		  gridview: true, 
		  height: 250, 
		  width: 700, 
		  onSelectRow: function(id){  
		      //document.location.href ="${showFolderUrl}/" + id; 
		  },
		  loadComplete: function() {			    	
		  }
		});		
		$( "#chooseFileDialog" ).dialog({
	          autoOpen: false,
	          height: 400,
	          width: 500,
	          modal: true,
	          resizable: false,
	          buttons:{
	        		 '${labelActionSave}':function(){
	        			
	        		},
	        		'${labelActionClose}':function(){
	        			$("#chooseFileDialog").dialog("close");	
	        		}  
	          }
	    });
	};
}
var globalSelectedFolderId = -1;            
$(function(){       
	
});	   
function showChooseFileDialog(){	  
	var dialog = $("#chooseFileDialog");
	dialog.dialog("open");
	refreshJQGrid('#listFolder', { page: 1, datatype: "json" });
}	