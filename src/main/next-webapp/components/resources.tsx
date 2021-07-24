import styles from '../styles/Home.module.css'


import { Resource } from '../pages/api/model/resouce'
import ResourceComponent from './resource'

export default function ResourcesComponent({ resources }: { resources: Resource[] }) {
  return (
    <><head className={styles.headingLg}>Resources</head>
      {resources?.map((resource, i) => {
        return (
          <article className={styles.card} key={i}>
            <ResourceComponent resource={resource} />
          </article>
        )
      })}
    </>)
}