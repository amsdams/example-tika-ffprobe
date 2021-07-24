import styles from '../styles/Home.module.css'

import { MediaType } from "../pages/api/model/mediaType"

export default function MediaTypeComponent({ mediaType }: { mediaType: MediaType }) {
    return (
        <><h3 className={styles.headingLg}>MediaType</h3>
            <p>name: {mediaType?.name}</p>
        </>)
}