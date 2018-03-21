/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


setInterval("nextChatUpdate()", 200);
setInterval("nextUserUpdate()", 200);

function newChatUpdate(xhr, status, args) {
    if (!args.messageStatus) {
        return;
    } else {
        //There are new messages, and we show from here.
        $('#chat').append('<div class="msg">[' + args.dateSent + '] <strong>' + args.user + '</strong>: ' + args.text + '</div>');
    }
}
function newUserUpdate(xhr, status, args) {
    if (!args.userOnlineStatus) {
            //no messages, no online users
            console.log("No online Users");
            $('#onlineUsers').html('<div class="msg">No user online.</div>');
            return;
        } else {
            //Show online users
            var onlineUsers = JSON.parse(args.onlineUsers);
            console.log(onlineUsers.length);
            var k = '<div class="msg">';

            for (i = 0; i < onlineUsers.length; i++) {
                k += '<strong>';
                k += onlineUsers[i];
                k += '</strong><br/>'
            }
            k += '</div>';

            $('#onlineUsers').html(k);
            //console.log("UserStatus:" + args.onlineUsers);
        }
}


function logInChat(){
    $(".chatGrid").toggle();
    $("#loginLink").hide();
    $("#logoutLink").show();
}

function logOutChat(){
    $(".chatGrid").toggle();
    $("#logoutLink").hide();
    $("#loginLink").show();
}
