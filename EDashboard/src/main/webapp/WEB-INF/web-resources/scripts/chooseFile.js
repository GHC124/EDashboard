function chooseFileDialog(){
	this.CHOOSE_MODE_SINGLE='single';
	this.CHOOSE_MODE_MUTILPLY='mutilply';
	
	this.globalSelectedFolderId=-1;
	this.minimunItem=0;
	this.maximunItem=0;
	this.chooseMode='';
	this.selectedItems=[];
	this.callbacks = {};	
	
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
	this.messageId='';
	this.messageLengthItem='';
	
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
	this.initLengthData = function(minItem,maxItem,mId,mLengthItem){
		this.minimunItem=minItem;
		this.maximunItem=maxItem;
		this.messageId=mId;
		this.messageLengthItem=mLengthItem;
	};
	this.initAllData = function(idData,baseData,folderData,fileData,lengthData){
		this.initId(idData[0], idData[1], idData[2], idData[3], idData[4]);
		this.initBase(baseData[0], baseData[1], baseData[2]);
		this.initFolderData(folderData[0], folderData[1], folderData[2]);
		this.initFileData(fileData[0], fileData[1], fileData[2], fileData[3], fileData[4]);
		this.initLengthData(lengthData[0], lengthData[1], lengthData[2], lengthData[3]);
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
			  current.filterCheckbox($(this), id);
		  },
		  loadComplete: function() {	
			  	var grid = $(this);
				addCheckbox2JQGrid(grid,'name','act','choose', current.listFileId + '_checkbox_',function(id, input){
					current.filterCheckbox(grid, id, input);
				});  
				current.populateSelectedCheckbox(grid);
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
	                		var length = current.selectedItems.length;
	                		if(length >= current.minimunItem && length <= current.maximunItem){
	                			$("#" + current.dialogId).dialog("close");
	                			current.executeCallback("saveClick", current.selectedItems);	   
	                		}else{
	                			var messageDiv = $('#' + current.messageId); 
	                			messageDiv.addClass('error');
	                			messageDiv.html(current.messageLengthItem);
	                			fadeInAndOut(messageDiv, 1000, 10000);
	                		}
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
	this.populateSelectedCheckbox = function(grid){
		var current = this;
		$(current.selectedItems).each(function(i){
			var checkbox = $(grid).find("#" + current.listFileId + "_checkbox_" + current.selectedItems[i]);
			if(checkbox){
				checkbox.prop('checked', true);
			}
		});		
	};
	this.filterCheckbox = function(grid, id, input){
		  var checkbox = $(grid).find('#' + this.listFileId + '_checkbox_' + id);
		  var checked = checkbox.prop('checked');
		  if(this.chooseMode == this.CHOOSE_MODE_SINGLE){
			  this.selectedItems=[];
			  var checkboxs = $(grid).find("input[id^='" + this.listFileId + "_checkbox_']");
			  checkboxs.each(function(){
				  $(this).prop('checked', false);					  
			  });
		  } 
		  if(checked){
			  if(input){
				  this.selectedItems.push(id);
				  checkbox.prop('checked', true);
			  }else{
				  unsetArray(this.selectedItems, id);
				  checkbox.prop('checked', false);
			  }
		  }else{
			  if(input){
				  unsetArray(this.selectedItems, id);
				  checkbox.prop('checked', false);
			  }else{
				  this.selectedItems.push(id);
				  checkbox.prop('checked', true);
			  }
		  }		  
	};
	this.show = function(cMode){
		if(cMode){
			if(cMode != this.CHOOSE_MODE_SINGLE && cMode != this.CHOOSE_MODE_MUTILPLY){
				log('Choose File Dialog: invalid choose mode! - ' + cMode);
				cMode = this.CHOOSE_MODE_SINGLE;
			}
		}else{
			log('Choose File Dialog: invalid choose mode!');
			cMode = this.CHOOSE_MODE_SINGLE;
		}
		this.chooseMode = cMode;
		this.globalSelectedFolderId = -1;
		var dialog = $("#" + this.dialogId);
		dialog.dialog("open");
		refreshJQGrid('#' + this.listFolderId, { page: 1, datatype: "json" });
		clearJQGridData($('#' + this.listFileId));
	};
	this.addListener = function(functionName, handler) {		
        if (functionName in  this.callbacks){
            this.callbacks[functionName].push(handler);
        }else{
            this.callbacks[functionName] = [handler];
        }
    };
    this.executeCallback = function(functionName, data){
    	for (var i=0; functionName in this.callbacks && i<this.callbacks[functionName].length; i++){
            this.callbacks[functionName][i](data);
    	}
    };
}	