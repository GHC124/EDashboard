var globalSelectedFolderId = -1;            
$(function(){       
	$("#listFolder").jqGrid({ 
	    url:'${showFileUrl}/folders', 
	    datatype: 'local', 
	    mtype: 'GET', 
	    colNames:['${labelFolderName}', '${labelFolderDescription}', '${labelActions}'], 
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
	    pager: '#pagerFolder', 
	    rowNum:5, 
	    rowList:[5,10,20,30,50], 
	    sortname: 'name', 
	    sortorder: 'asc', 
	    viewrecords: true, 
	    gridview: true, 
	    height: 150, 
	    width: 700, 
	    beforeRequest: function(){
	    	disableInput($('#btnNewFile'));
	    	//clearJQGridData($('#listFile'), {page : 1});
	    },
	    onSelectRow: function(id){ 
	    	globalSelectedFolderId = id;		    	
	    	refreshJQGrid('#listFile', { page: 1, postData: {folderId:id}, datatype: "json" });
	    },
	    loadComplete: function() {	
	    	var grid = $(this);
	        
	    }
	  });	    	
	$("#listFile").jqGrid({ 
	  url:'${showFileUrl}/listFile', 
	  datatype: 'local', 
	  mtype: 'GET', 
	  colNames:['${labelFileName}', '${labelFileDescription}', '${labelFileSize}', '${labelFileDateUp}'], 
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
	  pager: '#pagerFile', 
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
});	   
function showChooseFileDialog(){	  
	var dialog = $("#chooseFileDialog");
	dialog.dialog("open");
	refreshJQGrid('#listFolder', { page: 1, datatype: "json" });
}	