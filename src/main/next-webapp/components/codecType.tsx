import styles from '../styles/Home.module.css'

import { CodecType } from "../pages/api/model/codecType"

export default function CodecTypeComponent({ codecType }: { codecType: CodecType }) {
    return (
        <><h3 className={styles.headingLg}>CodecType</h3>
            <p>aname: {codecType?.name}</p>
        </>)
}