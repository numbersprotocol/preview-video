import type { PluginListenerHandle } from '@capacitor/core';

export type IOSPlayerDismissed = (data: any) => void;

export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  previewStartFromRemote(options: { url: string }): Promise<void>;
  previewStartFromLocal(options: { path: string }): Promise<void>;
  previewStop(): Promise<void>;
  playFullScreenFromRemote(options: { url: string }): Promise<void>;
  playFullScreenFromLocal(options: { path: string }): Promise<void>;
  stopFullScreen(): Promise<void>;
  addListener(
    eventName: 'iosPlayerDismissed',
    listenerFunc: IOSPlayerDismissed,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}
