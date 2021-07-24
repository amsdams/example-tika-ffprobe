import { CodecType } from "./codecType";
import { MediaType } from "./mediaType";

export interface Resource {
    name?: string;
    mediaType?: MediaType;
    codecTypes?: Array<CodecType>;
  }