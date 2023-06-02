export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  openVideoPlayer(options: any): Promise<any>;
  closeVideoPlayer(options: any): Promise<any>;
}
