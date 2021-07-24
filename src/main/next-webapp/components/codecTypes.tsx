import styles from '../styles/Home.module.css'

import { CodecType } from "../pages/api/model/codecType"
import CodecTypeComponent from './codecType'

export default function CodecTypesComponent({ codecTypes }: { codecTypes: CodecType[] }) {
    return (
        <><head className={styles.headingLg}>CodecTypes</head>
            {codecTypes?.map((codec, i) => {
        return (
          <article key={i}>
            <CodecTypeComponent codecType={codec} />
          </article>
        )
      })}
        </>)
}