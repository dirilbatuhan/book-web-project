import React from "react";
import "./App.css";
import {
  Container,
  Grid,
  Header,
  Table,
  Menu,
  Icon,
  Label,
} from "semantic-ui-react";
import { withRouter } from "react-router-dom";
import { toast } from "react-toastify";

class Dashboard extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      books: {},
      currentPage: 0,
    };
  }

  componentDidMount = () => {
    this.getBooks();
  };

  getBooks = () => {
    fetch(
      "http://localhost:8080/api/books/list-of-book?" +
        new URLSearchParams({ pageNumber: this.state.currentPage }),
      {
        method: "GET",
        header: {
          "Content-Type": "application/json",
        },
        credentials: "include",
      }
    )
      .then((r) => {
        if (r.ok) {
          return r;
        }
        if (r.status === 401 || r.status === 403 || r.status === 500) {
          return Promise.reject(new Error("Bir hata oluştu"));
        }
      })
      .then((r) => {
        return r.json();
      })
      .then((data) => {
        toast.info(`Toplamda ${data.totalElements} eleman bulundu`);
        this.setState({ books: data });
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  changePageTo = (i) => {
    this.setState({ currentPage: i }, this.getBooks);
  };

  render = () => {
    const { books } = this.state;
    return (
      <div className="App">
        <Container>
          <Grid>
            <Grid.Row columns="equal" centered>
              <Grid.Column width={16}>
                <Header size="huge">Kitaplar</Header>
                <Table celled>
                  <Table.Header>
                    <Table.Row>
                      <Table.HeaderCell colSpan="2" textAlign="center">
                        Kitap Adı
                      </Table.HeaderCell>
                      <Table.HeaderCell>Yazar</Table.HeaderCell>
                      <Table.HeaderCell>Yayınlanma Tarihi</Table.HeaderCell>
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    {books &&
                      books.content &&
                      books.content.map((value, index) => (
                        <Table.Row>
                          <Table.Cell>
                            <Label ribbon>
                              {books.size * books.number + (index + 1)}
                            </Label>
                          </Table.Cell>
                          <Table.Cell>{value.name}</Table.Cell>
                          <Table.Cell>{value.author}</Table.Cell>
                          <Table.Cell>{value.publishDate}</Table.Cell>
                        </Table.Row>
                      ))}
                  </Table.Body>
                  <Table.Footer>
                    <Table.Row>
                      <Table.HeaderCell colSpan="4">
                        <Menu floated="right" pagination>
                          <Menu.Item
                            onClick={() => {
                              this.changePageTo(this.state.currentPage - 1);
                            }}
                            as="a"
                            icon
                            disabled={books.first}
                          >
                            <Icon name="chevron left" />
                          </Menu.Item>
                          {[...Array(books.totalPages).keys()].map(
                            (value, index) => (
                              <Menu.Item
                                as="a"
                                onClick={() => {
                                  this.changePageTo(index);
                                }}
                                active={books.number === index}
                              >
                                {index + 1}
                              </Menu.Item>
                            )
                          )}
                          <Menu.Item
                            onClick={() => {
                              this.changePageTo(this.state.currentPage + 1);
                            }}
                            as="a"
                            icon
                            disabled={books.last}
                          >
                            <Icon name="chevron right" />
                          </Menu.Item>
                        </Menu>
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Footer>
                </Table>
              </Grid.Column>
            </Grid.Row>
          </Grid>
        </Container>
      </div>
    );
  };
}

export default withRouter(Dashboard);
