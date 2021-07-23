/*import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'

export default function Home(props) {
  const list =  (
    <ul>
      {props.posts.map((post) => (
        <li>{post.name}</li>
      ))}
    </ul>
  )

  return (
    <div>
      <h1>welcomeasd</h1>
{list}

    </div>
  )
}


export async function getServerSideProps(context) {
  // Call an external API endpoint to get posts.
  // You can use any data fetching library
  const res = await fetch('http://localhost:8080/resource/')
  const posts = await res.json()

  // By returning { props: { posts } }, the Blog component
  // will receive `posts` as a prop at build time
  return {
    props: {
      posts,
    },
  }
}
*/

export interface CodecType {
  name?: string;
}
export interface MediaType {
  name?: string;
}
export interface Resource {
  name?: string;
  mediaType?: MediaType;
  codecTypes?: Array<CodecType>;
}

import { NextPage } from 'next'

interface Props {
  posts?: Resource[];
}

const Home: NextPage<Props> = ({ posts }) => (
  <main>Your resources are: {posts?.map((post, i)=>{
    return (
      <div key={i}>
      <p>name: {post.name}</p>
      <p>media: {post.mediaType?.name}</p>
      {post.codecTypes?.map((codec, i)=>{
        return (
          <p key={i}>codec: {codec.name}</p>
        )
      })}
      </div>
    )
  })}</main>
)

Home.getInitialProps = async () => {
  const res = await fetch('http://localhost:8080/resource/')
  const posts = await res.json()
  return { posts }
}

export default Home