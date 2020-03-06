function deleteImg(obj) {
    console.log(obj.id);
    $.ajax({
        url: "/images/" + obj.id,
        type: 'delete',
        success:function(result){
            // 删除成功时,取出页面的该行元素
            var parentNode = obj.parentNode;
            var grandNode = parentNode.parentNode;
            var x = grandNode.parentNode;
            x.removeChild(grandNode);
            console.log("删除成功!");
        },
        error:function(){
            console.log("删除失败!");
        }
    });
}