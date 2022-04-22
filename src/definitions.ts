import type { PluginListenerHandle } from '@capacitor/core';

export type IOSPlayerDismissed = (data: any) => void;

export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  /** Not implemented for iOS, Android no usage in our case for now */
  previewStartFromRemote(options: { url: string }): Promise<{ value: string }>;
  /** Not implemented for iOS, Android no usage in our case for now */
  previewStartFromLocal(options: { path: string }): Promise<{ value: string }>;
  /** Not implemented for iOS, Android no usage in our case for now */
  previewStop(): Promise<{ value: string }>;
  playFullScreenFromRemote(options: { url: string }): Promise<{ value: string }>;
  playFullScreenFromLocal(options: { path: string }): Promise<{ value: string }>;
  stopFullScreen(): Promise<{ value: string }>;
  addListener(
    eventName: 'iosPlayerDismissed',
    listenerFunc: IOSPlayerDismissed,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}
