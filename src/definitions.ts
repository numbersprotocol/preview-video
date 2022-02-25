export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
