function set_or_remove(element, content){
    if(content !== undefined && content !== null) {
        element.html(content);
    }
    else{
        element.remove();
    }

}
function upper(title, description, button){
    set_or_remove($('#parallax_title'), title);
    set_or_remove($('#parallax_button'), button);
    set_or_remove($('#parallax_desc'), description);
}

