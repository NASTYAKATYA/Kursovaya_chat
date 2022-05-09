document.getElementById("searchButton").onclick = findRooms;
const tbody = Object.values(document.getElementsByTagName('tbody'));
const trElements = Object.values(tbody[0].children);
const trElementsNames = Object.values(trElements.map(el => el.firstElementChild.outerText));
const options = { includeScore: true }
const fuse = new Fuse(trElementsNames, options);

function removeTableRows()
{
    document.querySelectorAll("table tbody tr").forEach(function(e){e.remove()});
}

function fillRowsByIds(ids)
{
    removeTableRows()
    if (ids == null)
    {
        ids = Array.from(Array(trElements.length).keys())
    }
    for (var i = 0; i < ids.length; i++)
    {
        tbody[0].appendChild(trElements[ids[i]]);
    }
}

function findRooms()
{
    var searchString = document.getElementById('searchInput').value;
    if (!searchString)
    {
        fillRowsByIds();
        return;
    }
    var fuzeResult = fuse.search(searchString);
    var ids = [];
    for (var i = 0; i < fuzeResult.length; i++) {
        ids.push(fuzeResult[i].refIndex)
    }
    fillRowsByIds(ids);
}