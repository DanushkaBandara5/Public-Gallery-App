

const  btnUpload =$("header div button");
const overlayElm=$("#overlay");
const dropElm =$("#drop-area");
const mainElm =$("main");
const REST_API_URL="http://localhost:8080/gallery";
const cssLoaderHtml = `<div class="lds-facebook"><div></div><div></div><div></div></div>`;

loadAllImages();

btnUpload.on("click",()=>{
    overlayElm.removeClass("d-none");
});
overlayElm.on('click',(eventData)=>{
    if(eventData.target===overlayElm[0]){
        overlayElm.addClass('d-none');
    }
});
$(document).on('keydown',(eventData)=>{
    if(eventData.key==='escape' && !overlayElm.hasClass('d-none')){
        overlayElm.addClass('d-none');
    }
});

overlayElm.on('dragover',(eventData)=>{
    eventData.preventDefault();
});
overlayElm.on('drop',(eventData)=>{
    eventData.preventDefault();
});
dropElm.on('dragover',(eventData)=>{
    eventData.preventDefault();
});
dropElm.on('drop',(eventData)=>{
    eventData.preventDefault();
    const dropFiles = eventData.originalEvent.dataTransfer.files;
    const imageFiles= Array.from(dropFiles).filter(file=>file.type.startsWith("image/"));
    if(!imageFiles.length) return;
    overlayElm.addClass('d-none');
    uploadImages(imageFiles);
});

function uploadImages(images){
    const formData=new FormData();
    images.forEach(imageFile=>{
        const divElm =$('<div class="image loader"></div>');
        divElm.append(cssLoaderHtml);
        mainElm.append(divElm);
        formData.append("images",imageFile);

    });
    const jqxhr =$.ajax(`${REST_API_URL}/api/v1/images`,{
        method:'POST',
        data:formData,
        contentType:false, //by default jquery use application/x-www-form-urlencoded;
        processData:false //by default jquery try to convert data intoString;
    });
    jqxhr.done((imageList)=>{
        console.log(imageList[0])
        imageList.forEach(imageUrl=>{
            const divElm =$(".image.loader").first();
            divElm.removeClass('loader');
            divElm.empty();
            divElm.css('background-image',`url('${imageUrl}')`);

        });
    });
    // jqxhr.always(()=>$(".image.loader").remove());
}
function loadAllImages(){
    const jqxhr =$.ajax(`${REST_API_URL}/api/v1/images`);
    jqxhr.done((imgeList)=>{
        imgeList.forEach(imageUrl=>{
            const divElm =$('<div class="image"></div>');
            divElm.css('background-image',`url(${imageUrl})`);
            mainElm.append(divElm);
        });
    });
}