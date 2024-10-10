package ramble.sokol.tgm_inverse

@JsModule("@tonconnect/ui")
@JsNonModule
external class TonConnectUI(options: TonConnectUIOptions = definedExternally) {
    fun renderWalletSelector(elementId: String)
    // Include other methods and properties as needed
}

external interface TonConnectUIOptions {
    var manifestUrl: String
    // Include other options as needed
}