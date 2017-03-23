$(function() {
	$('#googleMap').hide()
	$(document)
			.on(
					"click",
					"#locateus",
					function(e) {
						e.preventDefault()
						$('#googleMap').show()
						$('#googleMap')
								.append(
										'<script>function initMap() { \
						var myLatLng = { \
							lat : -27.375169, \
							lng : 153.017855\
						};\
						var map = new google.maps.Map(document.getElementById("googleMap"), {\
							center : myLatLng,\
							zoom : 15\
						});\
						var marker = new google.maps.Marker({\
							position : myLatLng,\
							map : map,\
							title : "Aspley Family Day Care"\
						});\
					}</script>\
					<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2EtCnv_Hp8hNCH11ftk_i0QiDSk2wSS8&callback=initMap" type="text/javascript"></script>')
						location.href = "#googleMap";
					});

});
