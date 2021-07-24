import styles from '../styles/Home.module.css'
import { Resource } from '../pages/api/model/resouce'
import MediaTypeComponent from './mediaType'
import CodecTypesComponent from './codecTypes'
export default function ResourceComponent({ resource }: { resource: Resource }) {

  return (
    <><h2 className={styles.headingLg}>Resource</h2>

      <p>name: {resource.name}</p>
      {!resource.mediaType ? null : <MediaTypeComponent mediaType={resource.mediaType} />}

      <section className={styles.card}>
        {!resource.codecTypes ? null : <CodecTypesComponent codecTypes={resource.codecTypes} />}

      </section>


    </>)
}