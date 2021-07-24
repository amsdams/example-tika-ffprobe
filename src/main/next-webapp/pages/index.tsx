import styles from '../styles/Home.module.css'
import { NextPage } from 'next'
import { Resource } from './api/model/resouce'
import ResourcesComponent from '../components/resources'


interface Props {
  resources?: Resource[]
}

const Home: NextPage<Props> = ({ resources }) => (
  <main className={styles.main}>  <section className={styles.container}>
    {!resources?null: <ResourcesComponent resources={resources} />}
  </section>
  </main>

)

Home.getInitialProps = async () => {
  const res = await fetch('http://localhost:8080/resource/')
  const resources = await res.json()
  return { resources }
}

export default Home