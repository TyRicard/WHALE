
let maxWhaleID;
let minWhaleID;

function loadValidations(){

    let toValidate = document.getElementsByClassName("fv");

    for ( i in toValidate ){
        if ( toValidate[i].id == "weight" ){
            toValidate[i].required = true;
            toValidate[i].type = "number";
            toValidate[i].text = "Enter the weight in kg.";
            toValidate[i].min = "0"
        }
        else if ( toValidate[i].id == "whale-id" ){
            toValidate[i].required = true;
            toValidate[i].type = "text";
            toValidate[i].pattern = "(\\d+)(,\\s*\\d+)*";
            toValidate[i].setAttribute("onchange", "checkIDValues(this);");
            loadWhaleIDRangeRequirements( toValidate[i] );
            toValidate[i]
        }
    }
}

async function loadWhaleIDRangeRequirements( element ){
    const whaleIdResponse = await fetch( '/observations/getWhaleIdRange', { method: 'GET', headers: { 'Accept': 'application/txt+json'} } )
                .then( (data) => data.json() )
                .then( (data) => {
                        minWhaleID = Number(data['minWhaleID'])
                        maxWhaleID = Number(data['maxWhaleID'])
                } );

}

function checkIDValues( element ){
    element.pattern = "(\\d+)(,\\s*\\d+)*";
    if ( typeof element.value == undefined ) return; // Pattern validation will catch this case.
    if ( typeof minWhaleID == undefined || typeof maxWhaleID == undefined ){
        console.log("ERROR: min and max whale ids are not known. REST API call likely failed to recieve correct data.")
    }
    let vals = element.value.split( /[,\s]/ );
    for ( i in vals ){
        if ( vals[i] < maxWhaleID || vals[i] > minWhaleID ){
            element.type = "number";
            element.placeholder = "Error: IDs must be between " + maxWhaleID + " and " + minWhaleID;
            element.type = "text";
        }
    }

}