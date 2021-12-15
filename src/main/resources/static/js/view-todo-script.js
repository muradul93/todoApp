var vm = new Vue({
		el : "#page-content",
		data : {
			todoList : [],
			sysMsg : "",
			hasError : false,
			showModal : false,
			todoItem : {},
			status : "",
			startDate : "",
			endDate : ""
		},
		mounted : function(){
			this.fetchTodoList();
			var vm = this
		    $('#stdate').datepicker({
		      dateFormat : "dd-mm-yy",
		      onSelect: function(dateText) { 
		        vm.startDate = dateText
		      }
		   });
		   $('#nddate').datepicker({
			  dateFormat : "dd-mm-yy",
		      onSelect: function(dateText) { 
		        vm.endDate = dateText
		      }
		   });
		},
		methods : {
			fetchTodoList : function(e){
				this.todoList = [];
				let url = "/todo-items/all?search=query";
				if(this.status.length > 0){
					url += "&status="+this.status
				}
				if(this.startDate.length > 0){
					url += "&startDate="+this.startDate
				}
				if(this.endDate.length > 0){
					url += "&endDate="+this.endDate
				}
				axios.get(url)
				.then(function(response){
				if(response.status === 200){
					vm.todoList = response.data;
				}
				}).catch(function (error) {
		    		console.log(error);
		 	 	})
		  		.then(function () {
		    	
		  		});
			},
			deleteTodo : function(elementIndex){
				if(elementIndex >= 0){
					let con = confirm("Are you sure to delete the TODO?");
					if(!con)
						return;
					let todoItem = this.todoList[elementIndex];
					axios.get("/todo-items/delete/"+todoItem.itemId)
					.then(function(response){
					if(response.status === 200){
						vm.todoList.splice(elementIndex,1);
						vm.sysMsg = response.data;
						 setTimeout(function(){
							vm.sysMsg = "";
						},2000);
					}
					}).catch(function (error) {
						vm.hasError = true;
						vm.sysMsg = "Failed to process your request";
						 setTimeout(function(){
							vm.sysMsg = "";
						},2000);
			    		console.log(error);
			 	 	})
			  		.then(function () {
			    	
			  		});
				}
			},
			showTodoDetails : function(index){
				this.todoItem = this.todoList[index];
				this.showModal = true;
			}
		}
	});