/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$.keySize = 128;
$.iterationCount = 156;

var AesUtil = function () {
    this.keySize = $.keySize;
    this.iterationCount = $.iterationCount;
    this.iv = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);
    this.salt = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Hex);
    console.log(JSON.stringify(this));
};

AesUtil.prototype.Key = function (passPhrase) {
    var key = CryptoJS.PBKDF2(
            passPhrase,
            CryptoJS.enc.Hex.parse(this.salt),
            {keySize: this.keySize / 32, iterations: this.iterationCount});
    return key;
};

AesUtil.prototype.encrypt = function (passPhrase, plainText) {
    var key = this.Key(passPhrase);
    var encrypted = CryptoJS.AES.encrypt(
            plainText,
            key,
            {iv: CryptoJS.enc.Hex.parse(this.iv)});
    return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
};

AesUtil.prototype.decrypt = function (passPhrase, cipherText) {
    var key = this.Key(passPhrase);
    var cipherParams = CryptoJS.lib.CipherParams.create({
        ciphertext: CryptoJS.enc.Base64.parse(cipherText)
    });
    var decrypted = CryptoJS.AES.decrypt(
            cipherParams,
            key,
            {iv: CryptoJS.enc.Hex.parse(this.iv)});
    return decrypted.toString(CryptoJS.enc.Utf8);
};
