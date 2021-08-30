$(document).ready(function () {
			
			$('.myPhoto').css("height", 150); 
			$('.myPhoto').css("width", 180);
			$('.myPhoto').css('object-fit','cover');
			$('.myPhoto').css('object-position','top');
			
			
			// Cancel Button Click  -> Redirect in /
			$("#cancelBtn").on("click", function () {
				window.location = moduleURL;
				
			});
				
			
			
			// FILE UPLOAD
			$("#fileImage").change(function () {
				fileSize = this.files[0].size;
				
				if(fileSize > 1048576 ){	// 1024 x 1024 = 1048576
					this.setCustomValidity("Image must be less than 1 MB!!");
					this.reportValidity();
					
				
					
				} 
				else {
						this.setCustomValidity("");
							
						$('.myPhoto').css("height", 150); 
						$('.myPhoto').css("width", 180);
						$('.myPhoto').css('object-fit','cover');
						$('.myPhoto').css('object-position','top');
						
						
						showImageThumbnail(this);
				}
				
			
				
			});
		});
	
		// FILE UPLOAD
		function showImageThumbnail(fileInput) {
		var file = fileInput.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#thumbnail").attr("src",e.target.result);
		}
		
		reader.readAsDataURL(file);
	}