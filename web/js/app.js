/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var AES;
$(function () {

    AES = new AesUtil();

    $('#js').click(function () {
        var plaintext = $('#plaintext').val();
        var passphrase = $('#pass').val();
        var ciphertext = AES.encrypt(passphrase, plaintext);
        console.log(' Enc : ' + ciphertext);
        console.log(' Dec : ' + AES.decrypt(passphrase, ciphertext));
    });

    $('#ws').click(function () {
//                    var plaintext = $('#plaintext').val();
//                    var passphrase = $('#passphrase').val();
//                    var ciphertext = AES.encrypt(passphrase, plaintext);
//                    console.log('R Enc : ' + ciphertext);
//                    console.log('R Dec : ' + AES.decrypt(passphrase, ciphertext));
    });
});
