import axios from "axios";
import React, { useState } from "react";
import Waveform from "./waveForm.jsx";
import Button from '@mui/material/Button';
import LoadingButton from '@mui/lab/LoadingButton';
import Box from '@mui/material/Box';
import { IconButton } from "@mui/material";
import PauseCircleOutlineIcon from '@mui/icons-material/PauseCircleOutline';
import PlayCircleOutlineIcon from '@mui/icons-material/PlayCircleOutline';
import ReplayCircleFilledIcon from '@mui/icons-material/ReplayCircleFilled';

function WaveformContainer() {
    const [state, setState] = useState({
        url: { url: "", name: "" },
        isAtBeginning: true,
        isPlaying: false,
        start: 0,
        end: null,
        file: null,
        res: []
    })
    const [loading, setLoading] = useState(false);
    const { start, end, isPlaying, isAtBeginning, url, file, res } = state


    const togglePlay = () => {
        setState({
            ...state,
            isPlaying: !isPlaying,
            isAtBeginning: false,
            isPlayingTrimmed: false
        });
    }

    const resetPlayhead = () => {
        setState({
            ...state,
            isAtBeginning: true
        });
    }

    const fileUpload = (event) => {
        const file = event.target.files[0];
        console.log(file)
        const reader = new FileReader();
        reader.addEventListener(
            "load",
            () => {
                setState({
                    ...state,
                    url: { url: reader.result, name: file?.name },
                    file: file
                });
            },
            false
        );

        if (file) {
            reader.readAsDataURL(file);
        }
    }
    const onTrimming = (start, end) => {
        setState({
            ...state,
            start: Math.round(start * 1000),
            end: Math.round(end * 1000)
        })
    }
    const trim = () => {
        setLoading(true);
        var bodyFormData = new FormData();
        bodyFormData.append('file', file);
        bodyFormData.append('start', start);
        bodyFormData.append('end', end);
        console.log("end", end, typeof end)
        axios({
            method: "post",
            url: "http://localhost:8080/api/trimming-audio",
            data: bodyFormData,
            headers: { "Content-Type": "multipart/form-data" },
        })
            .then(function (response) {
                setLoading(false);
                console.log("response", response)
                setState({
                    ...state,
                    res: response.data.file
                })
            })
            .catch(function (response) {
                //handle error
                console.log(response);
            });
    }
    function downloadBase64File(contentType, base64Data, fileName) {
        const linkSource = `data:${contentType};base64,${base64Data}`;
        const downloadLink = document.createElement("a");
        downloadLink.href = linkSource;
        downloadLink.download = fileName;
        downloadLink.click();
    }
    const handleDownload = () => {
        downloadBase64File("audio/wav", res, file.name.replace(/\.[^/.]+$/, ""));
    }
    console.log("state", state)
    return (
        <React.Fragment>
            <div>
                AUDIO TRIMMING
            </div>
            <div>
                <input type="file" onChange={fileUpload} />
                <Waveform
                    src={url.url}
                    isPlaying={isPlaying}
                    isAtBeginning={isAtBeginning}
                    onTrimming={onTrimming}
                />
                {url.name}
                {url?.url &&
                    <div>
                        {
                            isPlaying ?
                                <IconButton size='large' aria-label="delete" onClick={() => togglePlay()}>
                                    <PauseCircleOutlineIcon size='large' />
                                </IconButton>
                                : <IconButton size='large' aria-label="delete" onClick={() => togglePlay()}>
                                    <PlayCircleOutlineIcon size='large' />
                                </IconButton>
                        }
                        <IconButton size='large' aria-label="delete" onClick={() => resetPlayhead()}>
                            <ReplayCircleFilledIcon />
                        </IconButton>
                    </div>
                }
                <Box p={1}>
                    {end && !res?.length ?
                        <LoadingButton
                            onClick={trim}
                            loading={loading}
                            variant="outlined"
                        >
                            Trim
                        </LoadingButton>
                        :
                        <Button variant="outlined" disabled>
                            Trim
                        </Button>}
                    {(res?.length) ?
                        <Button variant="outlined" onClick={handleDownload}>
                            Download
                        </Button>
                        : " "
                    }
                </Box>

            </div >
        </React.Fragment>

    );

}

export default React.memo(WaveformContainer);
