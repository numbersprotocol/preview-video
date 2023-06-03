export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  createVideoPlayer(options: any): Promise<any>;
  destroyVideoPlayer(options: any): Promise<any>;
}
