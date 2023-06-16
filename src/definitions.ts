export interface CreatePreviewVideoArgs {
  id: string;
  src: string;
  element: HTMLElement;
  config: PreviewVideoConfig;
}

export interface DestroyPreviewVideoArgs {
  id: string;
}

export interface OnScrollArgs {
  id: string;
  previewVideoBounds: {
    x: number;
    y: number;
    width: number;
    height: number;
  };
}

export interface PreviewVideoConfig {
  /**
   * Override width for native Video Player
   */
  width?: number;
  /**
   * Override height for native Video Player
   */
  height?: number;
  /**
   * Override absolute x coordinate position for native Video Player
   */
  x?: number;
  /**
   * Override absolute y coordinate position for native Video Player
   */
  y?: number;
  /**
   * Default location on the Earth towards which the camera points.
   */
}

export interface PreviewVideoPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  create(_args: CreatePreviewVideoArgs): Promise<any>;
  destroy(_args: DestroyPreviewVideoArgs): Promise<any>;
  onScroll(args: OnScrollArgs): Promise<void>;
}
