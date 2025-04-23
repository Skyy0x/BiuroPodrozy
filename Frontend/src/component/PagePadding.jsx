export default function PagePadding({children}) {
    return (
        <div className='lg:px-[150px] sm:px-[100px] px-[30px]'>
            {children}
        </div>
    )
}