function chooseFileDialog(){
	this.globalSelectedFolderId='';
	
	this.listFolderUrl='';
	this.listFileUrl='';
	this.labelFolderName='';
	this.labelFolderDescription='';
	this.labelActions='';
	this.labelFileName='';
	this.labelFileDescription='';
	this.labelFileSize='';
	this.labelFileDateUp='';
	this.labelActionSave='';
	this.labelActionClose='';	
	this.listFolderId='';
	this.listFileId='';
	this.pagerFolderId='';
	this.pagerFileId='';
	this.dialogId='';
	this.initId = function(cDialogId,lFolderId,pFolderId,lFileId,pFileId){
		this.listFolderId=lFolderId;
		this.listFileId=lFileId;
		this.pagerFolderId=pFolderId;
		this.pagerFileId=pFileId;
		this.dialogId=cDialogId;
	};
	this.initBase = function(lActions,lActionSave,lActionClose){
		this.labelActions=lActions;
		this.labelActionClose=lActionClose;
		this.labelActionSave=lActionSave;
	};
	this.initFolderData = function(lFolderUrl, lFolderName,lFolderDescription){
		this.listFolderUrl = lFolderUrl;
		this.labelFolderName=lFolderName;
		this.labelFolderDescription=lFolderDescription;
	};
	this.initFileData = function(lFileUrl,lFileName,lFileDescription,lFileSize,lFileDateUp){
		this.listFileUrl = lFileUrl;
		this.labelFileName=lFileName;
		this.labelFileDescription=lFileDescription;
		this.labelFileSize=lFileSize;
		this.labelFileDateUp=lFileDateUp;
	};
	this.initAllData = function(idData,baseData,folderData,fileData){
		this.initId(idData[0], idData[1], idData[2], idData[3], idData[4]);
		this.initBase(baseData[0], baseData[1], baseData[2]);
		this.initFolderData(folderData[0], folderData[1], folderData[2]);
		this.initFileData(fileData[0], fileData[1], fileData[2], fileData[3], fileData[4]);
	};
	this.initStructure = function(){
		var current = this;
		$("#" + this.listFolderId).jqGrid({ 
		    url: current.listFolderUrl, 
		    datatype: 'local', 
		    mtype: 'GET', 
		    colNames:[ current.labelFolderName, current.labelFolderDescription], 
		    colModel :[  
		      {name:'name', index:'name', width:150},
		      {name:'description', index:'description', sortable:false, width:150}		      
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
		    	
		    },
		    onSelectRow: function(id){ 
		    	current.globalSelectedFolderId = id;		    	
		    	refreshJQGrid('#' + current.listFileId, { page: 1, postData: {folderId:id}, datatype: "json" });
		    },
		    loadComplete: function() {	
		    			        
		    }
		  });	    	
		$("#" + this.listFileId).jqGrid({ 
		  url: current.listFileUrl, 
		  datatype: 'local', 
		  mtype: 'GET', 
		  colNames:[ current.labelFileName, current.labelFileDescription, current.labelFileSize, current.labelFileDateUp, current.labelActions], 
		  colModel :[  
		    {name:'name', index:'name', width:100},
		    {name:'description', index:'description', sortable:false, width:100},
		    {name:'sizeString', index:'size', width:50},
		    {name:'dateUpString', index:'dateUp', width:50},
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
		      
		  },
		  loadComplete: function() {	
			  var grid = $(this);
		  }
		});		
		$("#" + current.dialogId).dialog({
	          autoOpen: false,
	          height: 750,
	          width: 750,
	          modal: true,
	          resizable: false,
	          buttons:[
	                {   
	                	text: current.labelActionSave,
	                	click:function(){ 
	                		
	                	}
	                },
	                {
	                	text: current.labelActionClose,
	                	click:function(){
	                		$("#" + current.dialogId).dialog("close");
	                	}
	                }  
	          ]
	    });
	};
	this.show = function(){
		var dialog = $("#" + this.dialogId);
		dialog.dialog("open");
		refreshJQGrid('#' + this.listFolderId, { page: 1, datatype: "json" });
	};
}	