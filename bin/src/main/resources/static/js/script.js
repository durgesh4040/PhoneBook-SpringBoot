console.log("hi");
const toggleSidebar = () => {
        if ($(".sidebar").is(":visible")) {
            $(".sidebar").css("display", "none");
            $(".content").css("margin-left", "0%");
        } else {
            $(".sidebar").css("display", "block");
            $(".content").css("margin-left", "20%");
        }
    };
    
    
   const search = () => {
  let query = $("#search-input").val().trim(); // Get the value from the search input and remove leading/trailing whitespace

  if (query === "") {
    $(".search-result").hide(); // Hide search results if the query is empty
  } else {
    // Perform search or display results here
    console.log("Search query:", query);
    let url = `http://localhost:8080/search/${query}`;
    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);
        
        let text = '<div class="list-group">';
        data.forEach((contact) => {
          text += `<a href="/user/${contact.cid}/contact" class="list-group-item list-group-action">${contact.name}</a>`;
        });
        text += '</div>';
        $(".search-result").html(text);
        
        $(".search-result").show();
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  }
};

    
    
   // $(".search-result").show(); // Show search results
    


// script.js

// try payment 
const paymentstart = () => {
  console.log("start payment");
  let amount = $("#checkout").val();
  console.log(amount);
  
  if (amount === "" || amount === null) {
    console.log("not print");
    alert("Value is required!");
    return;
  }
}
 var jQuery = $.noConflict();


jQuery.ajax({
  url: 'user/create_order',
  type: 'POST',
  dataType: 'json',
  contentType: 'application/json',
  data: JSON.stringify({ amount:amount, info:'order_request' }),
  success: function(response) {
    console.log(response);
    // Handle the successful response here
  },
  error: function(error) {
    console.log(error);
    alert('Something went wrong');
    // Handle the error here
  }
});


if (typeof jQuery === 'undefined') {
  // jQuery is not loaded
  console.log('jQuery is not loaded.');
} else {
  // jQuery is loaded
  console.log('jQuery is loaded.');
}
