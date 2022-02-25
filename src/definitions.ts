export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  previewStartFromRemote(url: string): Promise<void>;
  previewStartFromLocal(path: string): Promise<void>;
  previewStop(): Promise<void>;
  playFullScreenFromRemote(url: string): Promise<void>;
  playFullScreenFromLocal(path: string): Promise<void>;
  stopFullScreen(): Promise<void>;
}
