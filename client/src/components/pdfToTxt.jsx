import React from 'react';
import { DropzoneArea } from 'material-ui-dropzone';
import { AttachFile, Description, PictureAsPdf } from '@material-ui/icons';
import Button from '@mui/material/Button';
import LoadingButton from '@mui/lab/LoadingButton';
import axios from 'axios';
import Box from '@mui/material/Box';

function PdfToTxt(props) {
    const [state, setState] = React.useState({ file: [], res: [] });
    const [loading, setLoading] = React.useState(false);

    const { file, res } = state
    function handleConvert() {
        setLoading(true);
        axios.get("https://reqres.in/api/users?page=2")
            .then((res) => {
                console.log("res", res)
                setLoading(false)
                setState({
                    ...state,
                    res: res.data.data
                })
            })
            .catch(err => console.log(err))
    }
    const handleAddFile = (file) => {
        setState({
            ...state,
            file: file,
            res: []
        })
    }
    const handlePreviewIcon = (fileObject, classes) => {
        const { type } = fileObject.file
        const iconProps = {
            className: classes.image,
        }
        return <PictureAsPdf {...iconProps} />
    }

    console.log("state", state)
    return (
        <React.Fragment>
            <DropzoneArea
                acceptedFiles={["application/pdf"]}
                onChange={handleAddFile}
                filesLimit={1}
                showFileNames={true}
                getPreviewIcon={handlePreviewIcon}
                maxFileSize={30000000}
                showPreviewsInDropzone={true}
            />
            <Box p={1}>
                {file?.length && !res?.length ?
                    <LoadingButton
                        onClick={handleConvert}
                        loading={loading}
                        variant="outlined"
                    >
                        Convert
                    </LoadingButton>
                    :
                    <Button variant="outlined" disabled>
                        Convert
                    </Button>}
                {(res?.length) ?
                    <Button variant="outlined">
                        Download
                    </Button>
                    : " "
                }
            </Box>
        </React.Fragment>


    );
}

export default PdfToTxt;