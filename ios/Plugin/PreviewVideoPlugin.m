#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(PreviewVideoPlugin, "PreviewVideo",
           CAP_PLUGIN_METHOD(echo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(playFullScreenFromRemote, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopFullScreen, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(method3, CAPPluginReturnCallback);
)
