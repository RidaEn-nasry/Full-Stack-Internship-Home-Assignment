const { useEffect, useState } = require("react");


const usePaginate = (totalRows, rowsPerPage) => {
    const [currentPage, setCurrentPage] = useState(1);



    // no decimal 
    const pageCount = Math.ceil(totalRows / rowsPerPage);

    const [pageNumbers, setPageNumbers] = useState([]);

    // num of pages to show on either side of current page
    const siblingCount = 1;

    useEffect(() => {
        let startPage = Math.max(1, currentPage - siblingCount);
        let endPage = Math.min(pageCount, currentPage + siblingCount);

        // array of pages to show
        let pages = [];
        for (let i = startPage; i <= endPage; i++) {
            pages.push(i);
        }

        setPageNumbers(pages);
    }, [currentPage, pageCount]);

    const paginate = (pageNumber) => {
        if (pageNumber < 1 || pageNumber > pageCount) return;
        setCurrentPage(pageNumber);
    }


    return { currentPage, paginate, pageNumbers };

}

export default usePaginate;
